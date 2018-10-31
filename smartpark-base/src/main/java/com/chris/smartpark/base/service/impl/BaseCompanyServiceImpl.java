package com.chris.smartpark.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.base.dao.BaseCompanyDao;
import com.chris.smartpark.base.entity.BaseCompanyEntity;
import com.chris.smartpark.base.service.BaseCompanyService;



@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {
	@Autowired
	private BaseCompanyDao baseCompanyDao;
	
	@Override
	public BaseCompanyEntity queryObject(Integer id){
		return baseCompanyDao.queryObject(id);
	}
	
	@Override
	public List<BaseCompanyEntity> queryList(Map<String, Object> map){
		return baseCompanyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return baseCompanyDao.queryTotal(map);
	}
	
	@Override
	public void save(BaseCompanyEntity baseCompany){
		baseCompanyDao.save(baseCompany);
	}
	
	@Override
	public void update(BaseCompanyEntity baseCompany){
		baseCompanyDao.update(baseCompany);
	}
	
	@Override
	public void delete(Integer id){
		baseCompanyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		baseCompanyDao.deleteBatch(ids);
	}
	
}
