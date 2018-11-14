package com.chris.smartpark.busi.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.*;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.dto.AuthorizeDTO;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.*;
import com.chris.smartpark.busi.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
	private VisitorInfoHisService visitorInfoHisService;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private VisitorIdcardService visitorIdcardService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public CommonResponse list(@RequestBody Map<String, Object> params){
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
	@GetMapping("/info/{id}")
	//@RequiresPermissions("busi:visitorreservation:info")重要操作前可加入权限校验
	public CommonResponse info(@PathVariable("id") Long id){
		ReservationDto reservation = visitorReservationService.queryObject(id);
		
		return CommonResponse.ok().put("ReservationDto", reservation);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/authentication")
	//@RequiresPermissions("busi:visitorreservation:info")重要操作前可加入权限校验
	public CommonResponse authentication(@RequestBody @Validated(VisitorIdcardEntity.ValidateIdentity.class)VisitorIdcardEntity visitorIdcardEntity,BindingResult result){
		CommonResponse commonResponse = new CommonResponse();
		log.info("========身份证识别开始并同步信息到门禁系统=====");
		try {
            ValidateUtils.validatedParams(result);
		//验证有无有效预约单
		List<VisitorReservationEntity> list = visitorReservationService.queryEffectRecord(visitorIdcardEntity.getIdcardNo());
			if(CollectionUtils.isEmpty(list)){
				commonResponse = CommonResponse.error("该身份证没有匹配的预约单信息");
			}else{
				String physicalCardId = visitorIdcardEntity.getPhysicalCardId();
				String idcardNo = visitorIdcardEntity.getIdcardNo();
				for(VisitorReservationEntity VisitorReservation : list){
					visitorIdcardEntity.setVisitorId(VisitorReservation.getVisitorId());
					//保存或更新信息到访客身份信息表
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("idcardNo",idcardNo);
					List<VisitorIdcardEntity> idcardEntityList  = visitorIdcardService.queryList(map);
					if(CollectionUtils.isEmpty(idcardEntityList)){
						visitorIdcardService.save(visitorIdcardEntity);
					}else{
						visitorIdcardService.update(idcardEntityList.get(0));
					}
					//组装入参调用门禁接口授权
					Date startTime = VisitorReservation.getAppointStartTime();
					Date endTime = VisitorReservation.getAppointEndTime();
					//保存身份证信息到身份信息表中
					log.info("========调用门禁接口授权成功=====");
					//更改预约单状态
					VisitorReservation.setStatus("5");
					visitorReservationService.update(VisitorReservation);
				}
				commonResponse = CommonResponse.ok();
			}
        }catch (CommonException e){
            log.error(e.getMessage());
            e.printStackTrace();
            commonResponse = CommonResponse.error(e.getMsg());
		}catch (Exception e){
			commonResponse = CommonResponse.error();
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return commonResponse;
	}
	
	/**
	 * 预约单保存
	 */
	@RequestMapping("/save")
	public CommonResponse save(@RequestBody  @Validated(ReservationDto.ValidateSaveReservation.class)ReservationDto reservationDto,BindingResult result){
		log.info("预约单生成入参"+ JSON.toJSONString(reservationDto));
		CommonResponse commonResponse = new CommonResponse();
		try {
			ValidateUtils.validatedParams(result);
			//查询系统配置判断是否配置了同行人详细信息(需要抽掉一个字典查询公共方法) 暂时放到第二阶段
			//1.保存访客信息（历史信息）
			VisitorInfoEntity visitorInfo = new VisitorInfoEntity();
			visitorInfo.setIdcardNo(reservationDto.getIdcardNo());
			VisitorInfoEntity rcd = visitorInfoService.selectByIdcardNo(visitorInfo);
			//保存身份信息
			VisitorIdcardEntity idcardEntity = reservationDto.getVisitorIdcardEntity();
			if(null!=idcardEntity){
				visitorIdcardService.save(idcardEntity);
			}
			if(null!=rcd){
				//删除现有记录并记录到历史记录表
				VisitorInfoHisEntity visitorInfoHis = new VisitorInfoHisEntity();
				BeanUtil.copyProperties(rcd,visitorInfoHis);
				visitorInfoHis.setCreateTime(DateUtils.currentDate());
				visitorInfoHis.setVisitorId(rcd.getId());
				visitorInfoHis.setId(null);
				visitorInfoHisService.save(visitorInfoHis);
				visitorInfoService.delete(rcd.getId());
				visitorInfo.setId(rcd.getId());
			}
			visitorInfo.setName(reservationDto.getName());
			visitorInfo.setPhone(reservationDto.getPhone());
			visitorInfo.setCreateTime(DateUtils.currentDate());
			visitorInfoService.save(visitorInfo);//此处可获取到visitorid = visitorInfo.getId()
			//2.保存预约单信息
			VisitorReservationEntity visitorReservation = new VisitorReservationEntity();
			BeanUtil.copyProperties(reservationDto, visitorReservation);
			visitorReservation.setVisitorId(visitorInfo.getId());
			visitorReservation.setCreateTime(DateUtils.currentDate());
			visitorReservation.setStatus("1");
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
		}catch (CommonException e){
			log.error(e.getMessage());
			e.printStackTrace();
			commonResponse = CommonResponse.error(e.getMsg());
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
    /**
     * 访客预约审核
     */
    @RequestMapping("/authorize")
    public CommonResponse authorize(@RequestBody AuthorizeDTO authorizeDTO){
		this.visitorReservationService.authorize(authorizeDTO);
		return CommonResponse.ok();
    }
}
