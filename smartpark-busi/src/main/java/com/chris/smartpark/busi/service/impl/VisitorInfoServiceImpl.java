package com.chris.smartpark.busi.service.impl;

import com.chris.base.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.busi.dao.VisitorInfoDao;
import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.smartpark.busi.service.VisitorInfoService;



@Service("visitorInfoService")
public class VisitorInfoServiceImpl implements VisitorInfoService {
	@Autowired
	private VisitorInfoDao visitorInfoDao;
	
	@Override
	public VisitorInfoEntity queryObject(Long id){
		return visitorInfoDao.queryObject(id);
	}
	
	@Override
	public List<VisitorInfoEntity> queryList(Map<String, Object> map){
		return visitorInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return visitorInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(VisitorInfoEntity visitorInfo){
		if (null != visitorInfo.getId()) {
			visitorInfo.setUpdateTime(DateUtils.currentDate());
			this.visitorInfoDao.update(visitorInfo);
		} else {
			this.visitorInfoDao.save(visitorInfo);
		}

	}
	@Override
	public VisitorInfoEntity selectByIdcardNo(VisitorInfoEntity visitorInfo){
		return visitorInfoDao.selectByIdcardNo(visitorInfo);
	}

	@Override
	public void update(VisitorInfoEntity visitorInfo){
		visitorInfoDao.update(visitorInfo);
	}
	
	@Override
	public void delete(Long id){
		visitorInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		visitorInfoDao.deleteBatch(ids);
	}
	
}
