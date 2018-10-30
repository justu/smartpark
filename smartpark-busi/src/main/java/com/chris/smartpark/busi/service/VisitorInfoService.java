package com.chris.smartpark.busi.service;

import com.chris.smartpark.busi.entity.VisitorInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 访客信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public interface VisitorInfoService {
	
	VisitorInfoEntity queryObject(Integer id);
	
	List<VisitorInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VisitorInfoEntity visitorInfo);
	
	void update(VisitorInfoEntity visitorInfo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
