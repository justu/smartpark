package com.chris.smartpark.busi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.dao.AuthenticationRecordDao;
import com.chris.smartpark.busi.dao.VisitorDoorRelDao;
import com.chris.smartpark.busi.dto.AuthorizeDto;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.*;
import com.chris.smartpark.busi.service.CarInfoService;
import com.chris.smartpark.busi.service.VisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.chris.smartpark.busi.dao.VisitorReservationDao;
import com.chris.smartpark.busi.service.VisitorReservationService;
import org.springframework.transaction.annotation.Transactional;


@Service("visitorReservationService")
public class VisitorReservationServiceImpl implements VisitorReservationService {
	@Autowired
	private VisitorReservationDao visitorReservationDao;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private VisitorInfoService visitorInfoService;
	@Autowired
	private BaseStaffService baseStaffService;
	@Autowired
	private VisitorDoorRelDao visitorDoorRelDao;
	@Autowired
	private AuthenticationRecordDao authenticationRecordDao;
	@Override
	public ReservationDto queryObject(Long id){
		ReservationDto reservationDto = new ReservationDto();
		//获取预约单信息
		VisitorReservationEntity visitorReservation =visitorReservationDao.queryObject(id);
		BeanUtil.copyProperties(visitorReservation,reservationDto);
		//访客
		VisitorInfoEntity visitorInfoEntity = visitorInfoService.queryObject(visitorReservation.getVisitorId());
		reservationDto.setName(visitorInfoEntity.getName());
		reservationDto.setIdcardNo(visitorInfoEntity.getIdcardNo());
		//受访者
		BaseStaffEntity baseStaffEntity = baseStaffService.queryObject(visitorReservation.getStaffId());
		reservationDto.setStaffId(baseStaffEntity.getId());
		reservationDto.setStaffName(baseStaffEntity.getName());
		reservationDto.setStaffPhone(baseStaffEntity.getMobile());
		//获取同行车辆信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reservationId",id);
		List<CarInfoEntity> list = carInfoService.queryList(map);
		reservationDto.setCarInfoEntitys(list);
		return reservationDto;
	}
	
	@Override
	public List<VisitorReservationEntity> queryList(Map<String, Object> map){
		return visitorReservationDao.queryList(map);
	}
	@Override
	public  List<VisitorReservationEntity> queryEffectRecord(String idcardNo){
		return visitorReservationDao.queryEffectRecord(idcardNo);
	}
	@Override
	public int queryTotal(Map<String, Object> map){
		return visitorReservationDao.queryTotal(map);
	}
	
	@Override
	public void save(VisitorReservationEntity visitorReservation){
		visitorReservationDao.save(visitorReservation);
	}
	
	@Override
	public void update(VisitorReservationEntity visitorReservation){
		visitorReservationDao.update(visitorReservation);
	}
	
	@Override
	public void delete(Long id){
		visitorReservationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		visitorReservationDao.deleteBatch(ids);
	}

	@Override
	public VisitorReservationEntity queryObjectById(Long id) {
		return visitorReservationDao.queryObject(id);
	}

    @Override
	@Transactional
    public JSONObject authorize(AuthorizeDto authorizeDto) {
		JSONObject returnJo  = new JSONObject();
		try{
			//1.查询预约单
			VisitorReservationEntity visitorReservationEntity =  visitorReservationDao.queryObject(authorizeDto.getReservationId());
			if(!"1".equals(visitorReservationEntity.getStatus())){
				//不是待审核状态无需处理
				returnJo.put("returnCode","0");
				returnJo.put("returnMessage","预约单不是待审核状态!");
				returnJo.put("returnData",new JSONObject());
				return returnJo;
			}

			//2.审核处理
			if(authorizeDto.getApproveReslut()==0){
				//审核不通过 修改预约记录表 插入审核不通过记录

				//发送通知短信

			}else if(authorizeDto.getApproveReslut()==1){
				//审核通过
				//2.保存访客门禁表
				List<VisitorDoorRelEntity> visitorDoorRelList = new ArrayList<VisitorDoorRelEntity>();
				for(Long doorId : authorizeDto.getDoorList()){
					VisitorDoorRelEntity visitorDoorRelEntity = new VisitorDoorRelEntity();
					visitorDoorRelEntity.setReservationId(authorizeDto.getReservationId());
					visitorDoorRelEntity.setDoorId(doorId);
					visitorDoorRelEntity.setVisitorId(visitorReservationEntity.getVisitorId());
					visitorDoorRelEntity.setStatus("1");
					visitorDoorRelList.add(visitorDoorRelEntity);
				}
				visitorDoorRelDao.saveBatch(visitorDoorRelList);

				//2.车辆授权
				String isAuthCar="0";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reservationId", visitorReservationEntity.getId());
				List<CarInfoEntity> carInfoList=carInfoService.queryList(params);
				if(!carInfoList.isEmpty()){
					//车道系统授权 to be continue;
					isAuthCar="1";
				}

				//3.保存授权记录表
				AuthenticationRecordEntity authenticationRecordEntity = new AuthenticationRecordEntity();
				authenticationRecordEntity.setReservationId(visitorReservationEntity.getId());
				authenticationRecordEntity.setVisitorId(visitorReservationEntity.getVisitorId());
				authenticationRecordEntity.setApplyBeginTime(visitorReservationEntity.getAppointEndTime());
				authenticationRecordEntity.setApplyEndTime(visitorReservationEntity.getAppointEndTime());
				authenticationRecordEntity.setAuthBeginTime(authorizeDto.getActStartTime());
				authenticationRecordEntity.setAuthEndTime(authorizeDto.getActEndTime());
				authenticationRecordEntity.setAuthTime(new Date());
				authenticationRecordEntity.setAuthCode("1"+isAuthCar);//第一位门禁 第二位车道 1已授权 0 未授权
				authenticationRecordEntity.setStatus("3");//2不通过 3 通过

				authenticationRecordDao.save(authenticationRecordEntity);


				//4.更新预约信息
				VisitorReservationEntity updateEntity = new VisitorReservationEntity();
				updateEntity.setId(visitorReservationEntity.getId());
				updateEntity.setActStartTime(authorizeDto.getActStartTime());
				updateEntity.setActEndTime(authorizeDto.getActEndTime());
				//授权时间 用updateTime
				updateEntity.setStatus("3");//2不通过 3通过

				//5.发送通知短信
				//to be continue;
			}else{
				//异常状态
				returnJo.put("returnCode","0");
				returnJo.put("returnMessage","参数异常!");
				returnJo.put("returnData",new JSONObject());
				return returnJo;
			}


		}catch (Exception e){
			e.printStackTrace();
			returnJo.put("returnCode","0");
			returnJo.put("returnMessage","系统异常!"+e.getMessage());
			returnJo.put("returnData",new JSONObject());
			return returnJo;
		}

		returnJo.put("returnCode","1");
		returnJo.put("returnMessage","处理成功!");
		returnJo.put("returnData",new JSONObject());
		return returnJo;
    }

}
