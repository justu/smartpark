package com.chris.smartpark.busi.service.impl;

import com.chris.smartpark.busi.dto.DoorControllerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.busi.dao.DoorDao;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.DoorService;



@Service("doorService")
public class DoorServiceImpl implements DoorService {
	@Autowired
	private DoorDao doorDao;
	
	@Override
	public DoorEntity queryObject(Long id){
		return doorDao.queryObject(id);
	}
	
	@Override
	public List<DoorEntity> queryList(Map<String, Object> map){
		return doorDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return doorDao.queryTotal(map);
	}
	
	@Override
	public void save(DoorEntity door){
		doorDao.save(door);
	}
	
	@Override
	public void update(DoorEntity door){
		doorDao.update(door);
	}
	
	@Override
	public void delete(Long id){
		doorDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		doorDao.deleteBatch(ids);
	}

	@Override
	public List<DoorControllerDTO> queryDoorControllersByOpenId(String openId) {
		return this.doorDao.queryDoorControllersByOpenId(openId);
	}
}
