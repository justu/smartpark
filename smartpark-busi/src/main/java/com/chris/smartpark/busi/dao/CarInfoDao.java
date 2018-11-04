package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.CarInfoEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
@Mapper
public interface CarInfoDao extends BaseDao<CarInfoEntity> {
	
}
