package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.AuthenticationDto;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 访客预约登记单
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
@Mapper
public interface VisitorReservationDao extends BaseDao<VisitorReservationEntity> {
    List<VisitorReservationEntity> queryEffectRecord(AuthenticationDto authenticationDto);
}
