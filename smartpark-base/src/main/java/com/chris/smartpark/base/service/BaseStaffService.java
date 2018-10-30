package com.chris.smartpark.base.service;

import com.chris.smartpark.base.entity.BaseStaffEntity;

import java.util.List;
import java.util.Map;

/**
 * 园区员工表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public interface BaseStaffService {
	
	BaseStaffEntity queryObject(Integer id);
	
	List<BaseStaffEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BaseStaffEntity baseStaff);
	
	void update(BaseStaffEntity baseStaff);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
