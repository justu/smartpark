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

import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.smartpark.busi.service.VisitorInfoService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 访客信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
@RestController
@RequestMapping("/app/visitorinfo")
public class VisitorInfoController {
	@Autowired
	private VisitorInfoService visitorInfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public CommonResponse list(@RequestBody Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<VisitorInfoEntity> visitorInfoList = visitorInfoService.queryList(query);
		int total = visitorInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(visitorInfoList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public CommonResponse info(@PathVariable("id") Long id){
		VisitorInfoEntity visitorInfo = visitorInfoService.queryObject(id);
		
		return CommonResponse.ok().put("visitorInfo", visitorInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public CommonResponse save(@RequestBody VisitorInfoEntity visitorInfo){
		visitorInfoService.save(visitorInfo);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public CommonResponse update(@RequestBody VisitorInfoEntity visitorInfo){
		visitorInfoService.update(visitorInfo);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public CommonResponse delete(@RequestBody Long[] ids){
		visitorInfoService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
