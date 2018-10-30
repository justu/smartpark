package com.chris.smartpark;

import com.alibaba.fastjson.JSONObject;
import com.chris.BaseApplication;
import com.chris.base.modules.sys.entity.SysUserEntity;
import com.chris.base.modules.sys.service.SysRoleService;
import com.chris.base.modules.sys.service.SysUserService;
import com.chris.smartpark.base.entity.BaseParkEntity;
import com.chris.smartpark.base.service.BaseDeviceInfoService;
import com.chris.smartpark.base.service.BaseParkService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
public class BaseTest {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private BaseParkService baseParkService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private BaseDeviceInfoService baseDeviceInfoService;
	@Test
	public void queryUser() {
		SysUserEntity user = this.sysUserService.queryByUserName("admin");
		System.out.println("查询用户信息：" + ToStringBuilder.reflectionToString(user));
	}

	@Test
	public void queryRole() {
		System.out.println("查询角色列表：" + JSONObject.toJSONString(this.sysRoleService.queryList(new HashMap<>())));
	}

	@Test
	public void queryPark() {
		BaseParkEntity parkEntity = this.baseParkService.queryObject(1);
		System.out.println("查询园区信息：" + JSONObject.toJSONString(parkEntity));
	}

	@Test
	public void queryDevice() {
		System.out.println("设备列表 ：" + JSONObject.toJSONString(this.baseDeviceInfoService.queryList(new HashMap<>())));
	}

}
