package com.chris.smartpark.base.dao;

import com.chris.smartpark.base.entity.BaseParkEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 园区信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 06.18
 */
@Mapper
public interface BaseParkDao extends BaseDao<BaseParkEntity> {
	
}
