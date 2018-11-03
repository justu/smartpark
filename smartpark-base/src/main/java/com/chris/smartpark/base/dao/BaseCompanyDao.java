package com.chris.smartpark.base.dao;

import com.chris.smartpark.base.entity.BaseCompanyEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 园区公司信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@Mapper
public interface BaseCompanyDao extends BaseDao<BaseCompanyEntity> {
	
}
