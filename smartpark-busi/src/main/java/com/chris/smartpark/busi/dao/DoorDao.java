package com.chris.smartpark.busi.dao;

import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门定义
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
@Mapper
public interface DoorDao extends BaseDao<DoorEntity> {
    List<DoorEntity> queryUserDoor(DoorEntity doorEntity);
}
