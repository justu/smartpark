package com.chris.smartpark.base.dao;

import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工用户
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Nov 15.18
 */
@Mapper
public interface BaseStaffDao extends BaseDao<BaseStaffEntity> {

    String queryParkNameByStaffMobile(String mobile);
}
