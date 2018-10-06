package com.chris.smartpark;

import com.alibaba.fastjson.JSONObject;
import com.chris.BusiApplication;
import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.smartpark.busi.service.VisitorInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusiApplication.class)
public class VisitorTest {
	@Autowired
	private VisitorInfoService visitorInfoService;

	@Test
	public void queryVisitorInfo() {
		VisitorInfoEntity visitorInfo = this.visitorInfoService.queryObject(1);
		System.out.println("查询访客信息：" + JSONObject.toJSONString(visitorInfo));
	}

}
