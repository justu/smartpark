package com.chris.smartpark.busi.service.impl;

import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.common.VisitorUtils;
import com.chris.smartpark.busi.dao.DoorDao;
import com.chris.smartpark.busi.dto.DoorControllerDTO;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.DoorService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



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
		Map<String, Object> params = Maps.newHashMap();
		if (!VisitorUtils.isAdminRole(openId)) {
			// 不是管理员角色，需要关联 openid 查询
			params.put(VisitorConstants.Keys.OPEN_ID, openId);
		}
		return this.doorDao.queryDoorControllersByOpenId(params);
	}
}
