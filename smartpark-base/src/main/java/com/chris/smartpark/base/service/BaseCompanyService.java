package com.chris.smartpark.base.service;

import com.chris.smartpark.base.entity.BaseCompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 园区公司信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
public interface BaseCompanyService {
	
	BaseCompanyEntity queryObject(Integer id);
	
	List<BaseCompanyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BaseCompanyEntity baseCompany);
	
	void update(BaseCompanyEntity baseCompany);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
