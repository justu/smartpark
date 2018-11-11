package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.VisitorDoorRelEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 访客门禁关系表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 09.18
 */
@Mapper
public interface VisitorDoorRelDao extends BaseDao<VisitorDoorRelEntity> {
	void saveBatch(List<VisitorDoorRelEntity> list);
}
