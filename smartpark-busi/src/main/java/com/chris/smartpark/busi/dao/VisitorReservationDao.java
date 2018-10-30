package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访客预约登记单
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
@Mapper
public interface VisitorReservationDao extends BaseDao<VisitorReservationEntity> {
	
}
