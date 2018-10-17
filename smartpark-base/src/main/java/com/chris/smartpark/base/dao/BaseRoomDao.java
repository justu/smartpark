package com.chris.smartpark.base.dao;

import com.chris.smartpark.base.entity.BaseRoomEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 园区房间信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@Mapper
public interface BaseRoomDao extends BaseDao<BaseRoomEntity> {
	
}
