package com.chris.smartpark.base.service.impl;

import com.chris.base.common.utils.ValidateUtils;
import com.chris.smartpark.base.dto.BaseStaffDTO;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.base.dao.BaseStaffDao;
import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.smartpark.base.service.BaseStaffService;



@Service("baseStaffService")
public class BaseStaffServiceImpl implements BaseStaffService {
	@Autowired
	private BaseStaffDao baseStaffDao;
	
	@Override
	public BaseStaffEntity queryObject(Long id){
		return baseStaffDao.queryObject(id);
	}
	
	@Override
	public List<BaseStaffEntity> queryList(Map<String, Object> map){
		return baseStaffDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return baseStaffDao.queryTotal(map);
	}
	
	@Override
	public void save(BaseStaffEntity baseStaff){
		baseStaffDao.save(baseStaff);
	}
	
	@Override
	public void update(BaseStaffEntity baseStaff){
		baseStaffDao.update(baseStaff);
	}
	
	@Override
	public void delete(Long id){
		baseStaffDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		baseStaffDao.deleteBatch(ids);
	}

	@Override
	public BaseStaffDTO queryByMobile(String mobile) {
		List<BaseStaffEntity> staffList = this.queryList(ImmutableMap.of("mobile", mobile));
		if (!ValidateUtils.isEmptyCollection(staffList)) {
			BaseStaffEntity baseStaffEntity = staffList.get(0);
			BaseStaffDTO baseStaffDTO = new BaseStaffDTO();
			BeanUtils.copyProperties(baseStaffEntity, baseStaffDTO);
			return baseStaffDTO;
		}
		return null;
	}
}
