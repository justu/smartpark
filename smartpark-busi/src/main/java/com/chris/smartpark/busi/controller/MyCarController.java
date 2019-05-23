package com.chris.smartpark.busi.controller;

import java.util.List;
import java.util.Map;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.annotation.Login;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.common.VisitorUtils;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.chris.smartpark.busi.entity.MyCarEntity;
import com.chris.smartpark.busi.service.MyCarService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import com.chris.base.common.utils.CommonResponse;




/**
 * 我的车辆表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since May 16.19
 */
@RestController
@RequestMapping("/app/mycar")
public class MyCarController {
	@Autowired
	private MyCarService myCarService;
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	@Login
	public CommonResponse list(@RequestBody Map<String, Object> params){
		if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.OPEN_ID))) {
			throw new CommonException("微信openId为空");
		}
        if (VisitorUtils.isAdminRole(params.get(VisitorConstants.Keys.OPEN_ID).toString())) {
            // 管理员角色可以查询所有
            params.remove(VisitorConstants.Keys.OPEN_ID);
        }
		//查询列表数据
        Query query = new Query(params);

		int total = myCarService.queryTotal(query);
		List<MyCarEntity> myCarList = total > 0 ? myCarService.queryList(query) : Lists.newArrayList();

		PageUtils pageUtil = new PageUtils(myCarList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put(VisitorConstants.Keys.PAGE, pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	@Login
	public CommonResponse info(@PathVariable("id") Long id){
		MyCarEntity myCar = myCarService.queryObject(id);
		
		return CommonResponse.ok().put("myCar", myCar);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	@Login
	public CommonResponse save(@RequestBody MyCarEntity myCar){
		myCarService.save(myCar);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	@Login
	public CommonResponse update(@RequestBody MyCarEntity myCar){
		myCarService.update(myCar);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@Login
	public CommonResponse delete(@RequestBody Long[] ids){
		myCarService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
