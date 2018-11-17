package com.chris.smartpark.base.service;

import com.chris.smartpark.base.dto.BaseStaffDTO;
import com.chris.smartpark.base.entity.BaseStaffEntity;

import java.util.List;
import java.util.Map;

/**
 * 员工用户
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 15.18
 */
public interface BaseStaffService {
	
	BaseStaffEntity queryObject(Long id);
	
	List<BaseStaffEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BaseStaffEntity baseStaff);
	
	void update(BaseStaffEntity baseStaff);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    BaseStaffDTO queryByMobile(String mobile);
}
