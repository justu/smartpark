package com.chris.smartpark.ibms.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.smartpark.ibms.entity.IBMSSubsystemEntity;
import com.chris.smartpark.ibms.service.IBMSSubsystemService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * ibms子系统表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 06.18
 */
@RestController
@RequestMapping("/ibms/ibmssubsystem")
public class IBMSSubsystemController {
	@Autowired
	private IBMSSubsystemService ibmsSubsystemService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ibms:ibmssubsystem:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<IBMSSubsystemEntity> ibmsSubsystemList = ibmsSubsystemService.queryList(query);
		int total = ibmsSubsystemService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ibmsSubsystemList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ibms:ibmssubsystem:info")
	public CommonResponse info(@PathVariable("id") Integer id){
		IBMSSubsystemEntity ibmsSubsystem = ibmsSubsystemService.queryObject(id);
		
		return CommonResponse.ok().put("ibmsSubsystem", ibmsSubsystem);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ibms:ibmssubsystem:save")
	public CommonResponse save(@RequestBody IBMSSubsystemEntity ibmsSubsystem){
		ibmsSubsystemService.save(ibmsSubsystem);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ibms:ibmssubsystem:update")
	public CommonResponse update(@RequestBody IBMSSubsystemEntity ibmsSubsystem){
		ibmsSubsystemService.update(ibmsSubsystem);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ibms:ibmssubsystem:delete")
	public CommonResponse delete(@RequestBody Integer[] ids){
		ibmsSubsystemService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
