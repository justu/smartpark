package com.chris.smartpark.busi.service;

import com.chris.smartpark.busi.dto.AuthorizeDTO;
import com.chris.smartpark.busi.dto.ReservationDTO;
import com.chris.smartpark.busi.entity.VisitorIdcardEntity;
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

	ReservationDTO queryObject(Long id);
	
	List<VisitorReservationEntity> queryList(Map<String, Object> map);

	List<VisitorReservationEntity> queryEffectRecord(String  idcardNo);

	int queryTotal(Map<String, Object> map);
	
	void save(VisitorReservationEntity visitorReservation);
	
	void update(VisitorReservationEntity visitorReservation);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	@Transactional
	void createReservationOrder(ReservationDTO reservationDTO);
	@Transactional
	void checkIdCardAndGetAuth(VisitorIdcardEntity visitorIdcardEntity);

	VisitorReservationEntity queryObjectById(Long id);

	@Transactional
	void approve(AuthorizeDTO authorizeDTO);

	/**
	 * 根据身份证号和状态查询预约单
	 * @param idcardNo
	 * @param status
	 * @return
	 */
    List<VisitorReservationEntity> queryByIdcardAndStatus(String idcardNo, String status);
}
