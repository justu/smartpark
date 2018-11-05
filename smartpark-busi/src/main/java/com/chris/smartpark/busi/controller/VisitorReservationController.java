package com.chris.smartpark.busi.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.chris.base.common.utils.*;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.entity.CarInfoEntity;
import com.chris.smartpark.busi.entity.ReservationDto;
import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.smartpark.busi.service.CarInfoService;
import com.chris.smartpark.busi.service.VisitorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.smartpark.busi.service.VisitorReservationService;


/**
 * 访客预约登记单
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
@Slf4j
@RestController
@RequestMapping("/app/visitorreservation")
public class VisitorReservationController {
	@Autowired
	private VisitorReservationService visitorReservationService;
	@Autowired
	private VisitorInfoService visitorInfoService;
	@Autowired
	private CarInfoService carInfoService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<VisitorReservationEntity> visitorReservationList = visitorReservationService.queryList(query);
		int total = visitorReservationService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(visitorReservationList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("busi:visitorreservation:info")重要操作前可加入权限校验
	public CommonResponse info(@PathVariable("id") Long id){
		VisitorReservationEntity visitorReservation = visitorReservationService.queryObject(id);
		
		return CommonResponse.ok().put("visitorReservation", visitorReservation);
	}
	
	/**
	 * 预约单保存
	 */
	@RequestMapping("/save")
	public CommonResponse save(@RequestBody  @Validated(ReservationDto.ValidateSaveReservation.class)ReservationDto reservationDto){
		log.info("预约单生成入参"+ JSON.toJSONString(reservationDto));
		CommonResponse commonResponse = new CommonResponse();
		try {
			//查询系统配置判断是否配置了同行人详细信息(需要抽掉一个字典查询公共方法) 暂时放到第二阶段
			//1.保存访客信息（历史信息）
			VisitorInfoEntity visitorInfo = new VisitorInfoEntity();
			visitorInfo.setName(reservationDto.getName());
			visitorInfo.setPhone(reservationDto.getPhone());
			visitorInfo.setIdcardNo(reservationDto.getIdcardNo());
			visitorInfo.setCreateTime(DateUtils.currentDate());
			visitorInfoService.save(visitorInfo);//此处可获取到visitorid = visitorInfo.getId()
			//2.保存预约单信息
			VisitorReservationEntity visitorReservation = new VisitorReservationEntity();
			BeanUtil.copyProperties(reservationDto, visitorReservation);
			visitorReservation.setVisitorId(visitorInfo.getId());
			visitorReservation.setCreateTime(DateUtils.currentDate());
			visitorReservationService.save(visitorReservation);
			//3.保存车牌信息
			if(1==reservationDto.getIsAddCarInfo()){
				List<CarInfoEntity> carInfoEntitys = reservationDto.getCarInfoEntitys();
				if(!CollectionUtils.isEmpty(carInfoEntitys)){
					for(CarInfoEntity carInfo :  carInfoEntitys){
						carInfo.setCreateTime(DateUtils.currentDate());
						carInfo.setReservationId(visitorReservation.getId());
					}
					carInfoService.batchInsert(carInfoEntitys);
				}
			}
			commonResponse = CommonResponse.ok();
			//4.保存同行人信息（暂缓）
			//校验参数自定义捕捉ValidatedUtil.validatedParams(result);
		}catch (Exception e){
			commonResponse = CommonResponse.error();
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return commonResponse;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public CommonResponse update(@RequestBody VisitorReservationEntity visitorReservation){
		visitorReservationService.update(visitorReservation);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public CommonResponse delete(@RequestBody Long[] ids){
		visitorReservationService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
