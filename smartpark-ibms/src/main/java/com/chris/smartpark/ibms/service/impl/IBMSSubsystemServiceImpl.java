package com.chris.smartpark.ibms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.ibms.dao.IBMSSubsystemDao;
import com.chris.smartpark.ibms.entity.IBMSSubsystemEntity;
import com.chris.smartpark.ibms.service.IBMSSubsystemService;



@Service("ibmsSubsystemService")
public class IBMSSubsystemServiceImpl implements IBMSSubsystemService {
	@Autowired
	private IBMSSubsystemDao ibmsSubsystemDao;
	
	@Override
	public IBMSSubsystemEntity queryObject(Integer id){
		return ibmsSubsystemDao.queryObject(id);
	}
	
	@Override
	public List<IBMSSubsystemEntity> queryList(Map<String, Object> map){
		return ibmsSubsystemDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ibmsSubsystemDao.queryTotal(map);
	}
	
	@Override
	public void save(IBMSSubsystemEntity ibmsSubsystem){
		ibmsSubsystemDao.save(ibmsSubsystem);
	}
	
	@Override
	public void update(IBMSSubsystemEntity ibmsSubsystem){
		ibmsSubsystemDao.update(ibmsSubsystem);
	}
	
	@Override
	public void delete(Integer id){
		ibmsSubsystemDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ibmsSubsystemDao.deleteBatch(ids);
	}
	
}
