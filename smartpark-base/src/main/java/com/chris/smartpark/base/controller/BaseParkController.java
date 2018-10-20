package com.chris.smartpark.base.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.smartpark.base.entity.BaseParkEntity;
import com.chris.smartpark.base.service.BaseParkService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 园区信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 06.18
 */
@RestController
@RequestMapping("/base/basepark")
public class BaseParkController {
	@Autowired
	private BaseParkService baseParkService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("base:basepark:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BaseParkEntity> baseParkList = baseParkService.queryList(query);
		int total = baseParkService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(baseParkList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("base:basepark:info")
	public CommonResponse info(@PathVariable("id") Integer id){
		BaseParkEntity basePark = baseParkService.queryObject(id);
		
		return CommonResponse.ok().put("basePark", basePark);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("base:basepark:save")
	public CommonResponse save(@RequestBody BaseParkEntity basePark){
		baseParkService.save(basePark);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("base:basepark:update")
	public CommonResponse update(@RequestBody BaseParkEntity basePark){
		baseParkService.update(basePark);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("base:basepark:delete")
	public CommonResponse delete(@RequestBody Integer[] ids){
		baseParkService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
