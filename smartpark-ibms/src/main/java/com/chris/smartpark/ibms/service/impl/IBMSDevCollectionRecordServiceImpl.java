package com.chris.smartpark.ibms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.ibms.dao.IBMSDevCollectionRecordDao;
import com.chris.smartpark.ibms.entity.IBMSDevCollectionRecordEntity;
import com.chris.smartpark.ibms.service.IBMSDevCollectionRecordService;



@Service("ibmsDevCollectionRecordService")
public class IBMSDevCollectionRecordServiceImpl implements IBMSDevCollectionRecordService {
	@Autowired
	private IBMSDevCollectionRecordDao ibmsDevCollectionRecordDao;
	
	@Override
	public IBMSDevCollectionRecordEntity queryObject(Integer id){
		return ibmsDevCollectionRecordDao.queryObject(id);
	}
	
	@Override
	public List<IBMSDevCollectionRecordEntity> queryList(Map<String, Object> map){
		return ibmsDevCollectionRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ibmsDevCollectionRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(IBMSDevCollectionRecordEntity ibmsDevCollectionRecord){
		ibmsDevCollectionRecordDao.save(ibmsDevCollectionRecord);
	}
	
	@Override
	public void update(IBMSDevCollectionRecordEntity ibmsDevCollectionRecord){
		ibmsDevCollectionRecordDao.update(ibmsDevCollectionRecord);
	}
	
	@Override
	public void delete(Integer id){
		ibmsDevCollectionRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ibmsDevCollectionRecordDao.deleteBatch(ids);
	}
	
}
