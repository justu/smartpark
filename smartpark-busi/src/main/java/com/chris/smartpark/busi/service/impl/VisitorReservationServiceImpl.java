package com.chris.smartpark.busi.service.impl;

import com.chris.smartpark.busi.dto.AuthenticationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.busi.dao.VisitorReservationDao;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.smartpark.busi.service.VisitorReservationService;



@Service("visitorReservationService")
public class VisitorReservationServiceImpl implements VisitorReservationService {
	@Autowired
	private VisitorReservationDao visitorReservationDao;
	
	@Override
	public VisitorReservationEntity queryObject(Long id){
		return visitorReservationDao.queryObject(id);
	}
	
	@Override
	public List<VisitorReservationEntity> queryList(Map<String, Object> map){
		return visitorReservationDao.queryList(map);
	}
	@Override
	public  List<VisitorReservationEntity> queryEffectRecord(AuthenticationDto authenticationDto){
		return visitorReservationDao.queryEffectRecord(authenticationDto);
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
