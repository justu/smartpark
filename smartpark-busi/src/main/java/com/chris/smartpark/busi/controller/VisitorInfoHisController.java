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

import com.chris.smartpark.busi.entity.VisitorInfoHisEntity;
import com.chris.smartpark.busi.service.VisitorInfoHisService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 访客信息历史表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
@RestController
@RequestMapping("/busi/visitorinfohis")
public class VisitorInfoHisController {
	@Autowired
	private VisitorInfoHisService visitorInfoHisService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("busi:visitorinfohis:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<VisitorInfoHisEntity> visitorInfoHisList = visitorInfoHisService.queryList(query);
		int total = visitorInfoHisService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(visitorInfoHisList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("busi:visitorinfohis:info")
	public CommonResponse info(@PathVariable("id") Integer id){
		VisitorInfoHisEntity visitorInfoHis = visitorInfoHisService.queryObject(id);
		
		return CommonResponse.ok().put("visitorInfoHis", visitorInfoHis);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("busi:visitorinfohis:save")
	public CommonResponse save(@RequestBody VisitorInfoHisEntity visitorInfoHis){
		visitorInfoHisService.save(visitorInfoHis);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("busi:visitorinfohis:update")
	public CommonResponse update(@RequestBody VisitorInfoHisEntity visitorInfoHis){
		visitorInfoHisService.update(visitorInfoHis);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("busi:visitorinfohis:delete")
	public CommonResponse delete(@RequestBody Integer[] ids){
		visitorInfoHisService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
