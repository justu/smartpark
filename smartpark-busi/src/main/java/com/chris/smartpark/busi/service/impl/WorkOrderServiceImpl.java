package com.chris.smartpark.busi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chris.smartpark.busi.dao.WorkOrderDao;
import com.chris.smartpark.busi.entity.WorkOrderEntity;
import com.chris.smartpark.busi.service.WorkOrderService;



@Service("workOrderService")
public class WorkOrderServiceImpl implements WorkOrderService {
	@Autowired
	private WorkOrderDao workOrderDao;
	
	@Override
	public WorkOrderEntity queryObject(Long id){
		return workOrderDao.queryObject(id);
	}
	
	@Override
	public List<WorkOrderEntity> queryList(Map<String, Object> map){
		return workOrderDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return workOrderDao.queryTotal(map);
	}
	
	@Override
	public void save(WorkOrderEntity workOrder){
		workOrder.setStatus(1); // 设置状态为新建
		this.workOrderDao.save(workOrder);
	}
	
	@Override
	public void update(WorkOrderEntity workOrder){
		workOrderDao.update(workOrder);
	}
	
	@Override
	public void delete(Long id){
		workOrderDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		workOrderDao.deleteBatch(ids);
	}
	
}
