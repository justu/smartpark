package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.VisitorInfoHisEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 访客信息历史表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
@Mapper
public interface VisitorInfoHisDao extends BaseDao<VisitorInfoHisEntity> {
	
}
