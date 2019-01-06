package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.AuthenticationRecordEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约审核表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 11.18
 */
@Mapper
public interface AuthenticationRecordDao extends BaseDao<AuthenticationRecordEntity> {
	
}
