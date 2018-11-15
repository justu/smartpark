package com.chris.smartpark.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.base.dao.BaseOrganizationDao;
import com.chris.smartpark.base.entity.BaseOrganizationEntity;
import com.chris.smartpark.base.service.BaseOrganizationService;



@Service("baseOrganizationService")
public class BaseOrganizationServiceImpl implements BaseOrganizationService {
	@Autowired
	private BaseOrganizationDao baseOrganizationDao;
	
	@Override
	public BaseOrganizationEntity queryObject(Long id){
		return baseOrganizationDao.queryObject(id);
	}
	
	@Override
	public List<BaseOrganizationEntity> queryList(Map<String, Object> map){
		return baseOrganizationDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return baseOrganizationDao.queryTotal(map);
	}
	
	@Override
	public void save(BaseOrganizationEntity baseOrganization){
		baseOrganizationDao.save(baseOrganization);
	}
	
	@Override
	public void update(BaseOrganizationEntity baseOrganization){
		baseOrganizationDao.update(baseOrganization);
	}
	
	@Override
	public void delete(Long id){
		baseOrganizationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		baseOrganizationDao.deleteBatch(ids);
	}
	
}
