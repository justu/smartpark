package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访客表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 07.18
 */
@Mapper
public interface VisitorInfoDao extends BaseDao<VisitorInfoEntity> {
	
}
