package com.chris.smartpark.busi.controller;

import com.alibaba.fastjson.JSON;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.smartpark.busi.dto.AuthorizeDTO;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.VisitorIdcardEntity;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.smartpark.busi.service.CarInfoService;
import com.chris.smartpark.busi.service.VisitorInfoService;
import com.chris.smartpark.busi.service.VisitorReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
			visitorReservationService.checkIdCardAndGetAuth(visitorIdcardEntity);
			commonResponse = CommonResponse.ok();
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
			visitorReservationService.createReservation(reservationDto);
			commonResponse = CommonResponse.ok();
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
    @RequestMapping("/approve")
    public CommonResponse approve(@RequestBody AuthorizeDTO authorizeDTO){
		this.visitorReservationService.approve(authorizeDTO);
		return CommonResponse.ok();
    }
}
