package com.chris.smartpark.busi.service;

import com.chris.smartpark.busi.entity.CompanionsEntity;

import java.util.List;
import java.util.Map;

/**
 * 同行人员信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public interface CompanionsService {
	
	CompanionsEntity queryObject(Integer id);
	
	List<CompanionsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CompanionsEntity companions);
	
	void update(CompanionsEntity companions);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
