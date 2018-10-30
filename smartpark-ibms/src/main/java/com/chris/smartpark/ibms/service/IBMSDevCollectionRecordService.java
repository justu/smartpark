package com.chris.smartpark.ibms.service;

import com.chris.smartpark.ibms.entity.CountElectricityEntity;
import com.chris.smartpark.ibms.entity.EnvInfoEntity;
import com.chris.smartpark.ibms.entity.IBMSDevCollectionRecordEntity;
import com.chris.smartpark.ibms.entity.MonthElectricityEntity;

import java.util.List;
import java.util.Map;

/**
 * 设备采集记录表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
public interface IBMSDevCollectionRecordService {
	
	IBMSDevCollectionRecordEntity queryObject(Integer id);
	
	List<IBMSDevCollectionRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(IBMSDevCollectionRecordEntity ibmsDevCollectionRecord);
	
	void update(IBMSDevCollectionRecordEntity ibmsDevCollectionRecord);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	List<MonthElectricityEntity> queryElectricityInMonth();

	CountElectricityEntity countElectricity();

	List<EnvInfoEntity> queryEnvInfo();
}
