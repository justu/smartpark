package com.chris.smartpark.base.service;

import com.chris.smartpark.base.entity.BaseParkEntity;

import java.util.List;
import java.util.Map;

/**
 * 园区信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 06.18
 */
public interface BaseParkService {
	
	BaseParkEntity queryObject(Integer id);
	
	List<BaseParkEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BaseParkEntity basePark);
	
	void update(BaseParkEntity basePark);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
