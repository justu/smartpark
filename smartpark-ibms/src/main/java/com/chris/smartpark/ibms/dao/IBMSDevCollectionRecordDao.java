package com.chris.smartpark.ibms.dao;

import com.chris.base.modules.sys.dao.BaseDao;
import com.chris.smartpark.ibms.entity.EnvInfoEntity;
import com.chris.smartpark.ibms.entity.IBMSDevCollectionRecordEntity;
import com.chris.smartpark.ibms.entity.MonthElectricityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备采集记录表
 *
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
@Mapper
@Component
public interface IBMSDevCollectionRecordDao extends BaseDao<IBMSDevCollectionRecordEntity> {

    List<MonthElectricityEntity> queryElectricityInMonth();

    Double countElectricityInYesterday();

    Double countElectricityInLastMonth();

    Double countElectricityInMonth();

    List<EnvInfoEntity> queryEnvInfo();

}
