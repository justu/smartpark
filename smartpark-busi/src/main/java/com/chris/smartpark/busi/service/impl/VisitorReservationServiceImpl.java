package com.chris.smartpark.busi.service.impl;

import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.dto.AuthenticationDto;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.CarInfoEntity;
import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.smartpark.busi.service.CarInfoService;
import com.chris.smartpark.busi.service.VisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chris.smartpark.busi.dao.VisitorReservationDao;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.smartpark.busi.service.VisitorReservationService;



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
	
}
