package com.chris.smartpark.base.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 园区员工表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 11.18
 */
@RestController
@RequestMapping("/base/basestaff")
public class BaseStaffController {
	@Autowired
	private BaseStaffService baseStaffService;
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("base:basestaff:list")
	public CommonResponse list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BaseStaffEntity> baseStaffList = baseStaffService.queryList(query);
		int total = baseStaffService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(baseStaffList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("base:basestaff:info")
	public CommonResponse info(@PathVariable("id") Long id){
		BaseStaffEntity baseStaff = baseStaffService.queryObject(id);
		
		return CommonResponse.ok().put("baseStaff", baseStaff);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("base:basestaff:save")
	public CommonResponse save(@RequestBody BaseStaffEntity baseStaff){
		baseStaffService.save(baseStaff);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	@RequiresPermissions("base:basestaff:update")
	public CommonResponse update(@RequestBody BaseStaffEntity baseStaff){
		baseStaffService.update(baseStaff);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("base:basestaff:delete")
	public CommonResponse delete(@RequestBody Long[] ids){
		baseStaffService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
