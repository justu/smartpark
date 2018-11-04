package com.chris.smartpark.busi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.busi.dao.VisitorInfoHisDao;
import com.chris.smartpark.busi.entity.VisitorInfoHisEntity;
import com.chris.smartpark.busi.service.VisitorInfoHisService;



@Service("visitorInfoHisService")
public class VisitorInfoHisServiceImpl implements VisitorInfoHisService {
	@Autowired
	private VisitorInfoHisDao visitorInfoHisDao;
	
	@Override
	public VisitorInfoHisEntity queryObject(Long id){
		return visitorInfoHisDao.queryObject(id);
	}
	
	@Override
	public List<VisitorInfoHisEntity> queryList(Map<String, Object> map){
		return visitorInfoHisDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return visitorInfoHisDao.queryTotal(map);
	}
	
	@Override
	public void save(VisitorInfoHisEntity visitorInfoHis){
		visitorInfoHisDao.save(visitorInfoHis);
	}
	
	@Override
	public void update(VisitorInfoHisEntity visitorInfoHis){
		visitorInfoHisDao.update(visitorInfoHis);
	}
	
	@Override
	public void delete(Long id){
		visitorInfoHisDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		visitorInfoHisDao.deleteBatch(ids);
	}
	
}
