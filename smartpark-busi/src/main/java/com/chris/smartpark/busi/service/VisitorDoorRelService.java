package com.chris.smartpark.busi.service;

import com.chris.smartpark.busi.entity.VisitorDoorRelEntity;

import java.util.List;
import java.util.Map;

/**
 * 访客门禁关系表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 09.18
 */
public interface VisitorDoorRelService {
	
	VisitorDoorRelEntity queryObject(Long id);
	
	List<VisitorDoorRelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VisitorDoorRelEntity visitorDoorRel);
	
	void update(VisitorDoorRelEntity visitorDoorRel);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void saveBatch(List<VisitorDoorRelEntity> list);
}
