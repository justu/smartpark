package com.chris.smartpark.busi.controller;

import com.alibaba.fastjson.JSON;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.annotation.Login;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.dto.ReservationOrderApproveDTO;
import com.chris.smartpark.busi.dto.ReservationOrderDTO;
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
	 * 根据条件分页查询员工或管理员对应的预约单
	 * @param params
	 * @return
	 */
	@PostMapping("/queryReservationOrdersByOpenId")
	public CommonResponse queryReservationOrdersByOpenId(@RequestBody Map<String, Object> params){
		if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.OPEN_ID))) {
			return CommonResponse.error("openId为空！");
		}
		if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.TYPE))) {
			return CommonResponse.error("type为空！");
		}
		if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.PAGE))) {
			params.put(VisitorConstants.Keys.PAGE, VisitorConstants.Page.PAGE_NO + "");
		}
		if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.LIMIT))) {
			params.put(VisitorConstants.Keys.LIMIT, VisitorConstants.Page.PAGE_SIZE + "");
		}
		PageUtils pageUtil = this.visitorReservationService.queryReservationOrdersByOpenId(params);
		return CommonResponse.ok().put("page", pageUtil);
	}


	
	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public CommonResponse info(@PathVariable("id") Long id){
		ReservationOrderDTO reservationOrder = visitorReservationService.queryReservationOrderDetail(id);
		return CommonResponse.ok().setData(reservationOrder);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/authentication")
	public CommonResponse authentication(@RequestBody @Validated(VisitorIdcardEntity.ValidateIdentity.class)VisitorIdcardEntity visitorIdcardEntity,BindingResult result){
		log.info("========身份证识别开始并同步信息到门禁系统=====");
		ValidateUtils.validatedParams(result);
		visitorReservationService.checkIdCardAndGetAuth(visitorIdcardEntity);
		return CommonResponse.ok();
	}
	
	/**
	 * 预约单保存
	 */
	@RequestMapping("/save")
	@Login
	public CommonResponse save(@RequestBody  @Validated(ReservationOrderDTO.ValidateSaveReservation.class)ReservationOrderDTO reservationOrderDTO, BindingResult result){
		log.info("预约单生成入参"+ JSON.toJSONString(reservationOrderDTO));
		ValidateUtils.validatedParams(result);
		visitorReservationService.createReservationOrder(reservationOrderDTO);
		return CommonResponse.ok();
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
	@Login
	public CommonResponse delete(@RequestBody Long[] ids){
		visitorReservationService.deleteBatch(ids);
		return CommonResponse.ok();
	}
    /**
     * 访客预约审核
     */
    @RequestMapping("/approve")
	@Login
    public CommonResponse approve(@RequestBody ReservationOrderApproveDTO authorizeDTO){
		this.visitorReservationService.approve(authorizeDTO);
		return CommonResponse.ok();
    }
}
