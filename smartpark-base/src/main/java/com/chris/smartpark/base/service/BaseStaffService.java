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

    List<BaseStaffDTO> queryStaffListByOrgId(String orgId);

    int queryTotal(Map<String, Object> map);
	
	void save(BaseStaffEntity baseStaff);
	
	void update(BaseStaffEntity baseStaff);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    BaseStaffDTO queryByMobile(String mobile);

	/**
	 * 根据员工手机号查询所有园区名称
	 * @param mobile
	 * @return
	 */
	String queryParkNameByStaffMobile(String mobile);
}
