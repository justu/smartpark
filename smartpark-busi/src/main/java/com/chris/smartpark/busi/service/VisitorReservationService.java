package com.chris.smartpark.busi.service;

import com.chris.smartpark.busi.entity.VisitorReservationEntity;

import java.util.List;
import java.util.Map;

/**
 * 访客预约登记单
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 11.18
 */
public interface VisitorReservationService {
	
	VisitorReservationEntity queryObject(Long id);
	
	List<VisitorReservationEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(VisitorReservationEntity visitorReservation);
	
	void update(VisitorReservationEntity visitorReservation);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
