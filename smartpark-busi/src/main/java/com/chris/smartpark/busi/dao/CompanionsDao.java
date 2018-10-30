package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.CompanionsEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 同行人员信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
@Mapper
public interface CompanionsDao extends BaseDao<CompanionsEntity> {
	
}
