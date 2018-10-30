package com.chris.smartpark.busi.service;

import com.chris.smartpark.busi.entity.VisitorInfoHisEntity;

import java.util.List;
import java.util.Map;

/**
 * 访客信息历史表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public interface VisitorInfoHisService {
	
	VisitorInfoHisEntity queryObject(Integer id);
	
	List<VisitorInfoHisEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VisitorInfoHisEntity visitorInfoHis);
	
	void update(VisitorInfoHisEntity visitorInfoHis);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
