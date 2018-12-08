package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.dto.ReservationOrderQryDTO;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 访客预约登记单
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
@Mapper
public interface VisitorReservationDao extends BaseDao<VisitorReservationEntity> {
    List<VisitorReservationEntity> queryEffectRecord(String idcardNo);

    /**
     * 更新预约单状态
     * @param visitorReservationEntity
     */
    void updateStatus(VisitorReservationEntity visitorReservationEntity);

    List<VisitorReservationEntity> queryByIdcardAndStatus(@Param("idcardNo") String idcardNo, @Param("status") String status);

    List<ReservationOrderQryDTO> queryReservationOrdersByOpenId(Map<String, Object> params);

    List<VisitorReservationEntity> queryByStatusAndTime();

    List<VisitorReservationEntity> queryByStatusAndTime2();

    int countReservationOrdersByOpenId(Map<String, Object> params);
}
