package com.chris.smartpark.busi.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.smartpark.busi.entity.CompanionsEntity;
import com.chris.smartpark.busi.service.CompanionsService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 同行人员信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
@RestController
@RequestMapping("/busi/companions")
public class CompanionsController {
	@Autowired
	private CompanionsService companionsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("busi:companions:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CompanionsEntity> companionsList = companionsService.queryList(query);
		int total = companionsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(companionsList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("busi:companions:info")
	public CommonResponse info(@PathVariable("id") Long id){
		CompanionsEntity companions = companionsService.queryObject(id);
		
		return CommonResponse.ok().put("companions", companions);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("busi:companions:save")
	public CommonResponse save(@RequestBody CompanionsEntity companions){
		companionsService.save(companions);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("busi:companions:update")
	public CommonResponse update(@RequestBody CompanionsEntity companions){
		companionsService.update(companions);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("busi:companions:delete")
	public CommonResponse delete(@RequestBody Long[] ids){
		companionsService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
