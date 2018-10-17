package com.chris.smartpark.ibms.dao;

import com.chris.smartpark.ibms.entity.IBMSDevAlarmRecordEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备报警记录表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@Mapper
public interface IBMSDevAlarmRecordDao extends BaseDao<IBMSDevAlarmRecordEntity> {
	
}
