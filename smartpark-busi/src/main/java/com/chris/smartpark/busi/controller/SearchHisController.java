package com.chris.smartpark.busi.controller;

import java.util.List;
import java.util.Map;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.annotation.Login;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.chris.smartpark.busi.entity.SearchHisEntity;
import com.chris.smartpark.busi.service.SearchHisService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 搜索历史表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since May 15.19
 */
@RestController
@RequestMapping("/app/searchhis")
public class SearchHisController {
	@Autowired
	private SearchHisService searchHisService;
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	@Login
	public CommonResponse list(@RequestBody Map<String, Object> params){
		if (ValidateUtils.isEmpty(params.get("openId"))) {
			throw new CommonException("openId为空");
		}
		//查询列表数据
        Query query = new Query(params);

		List<SearchHisEntity> searchHisList = searchHisService.queryList(query);
		int total = searchHisService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(searchHisList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	@Login
	public CommonResponse save(@RequestBody SearchHisEntity searchHis){
		searchHisService.save(searchHis);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@Login
	public CommonResponse delete(@RequestBody Long[] ids){
		searchHisService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
