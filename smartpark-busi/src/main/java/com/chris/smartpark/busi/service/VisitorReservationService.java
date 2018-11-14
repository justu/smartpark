package com.chris.smartpark.busi.service;

import com.alibaba.fastjson.JSONObject;
import com.chris.smartpark.busi.dto.AuthorizeDTO;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import org.springframework.transaction.annotation.Transactional;

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

	ReservationDto queryObject(Long id);
	
	List<VisitorReservationEntity> queryList(Map<String, Object> map);

	List<VisitorReservationEntity> queryEffectRecord(String  idcardNo);

	int queryTotal(Map<String, Object> map);
	
	void save(VisitorReservationEntity visitorReservation);
	
	void update(VisitorReservationEntity visitorReservation);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	VisitorReservationEntity queryObjectById(Long id);

	@Transactional
	void approve(AuthorizeDTO authorizeDTO);
}
