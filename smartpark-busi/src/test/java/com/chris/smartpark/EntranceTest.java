package com.chris.smartpark;

import com.alibaba.fastjson.JSONObject;
import com.chris.BusiApplication;
import com.chris.base.common.tree.TreeNode;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.EntranceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusiApplication.class)
public class EntranceTest {
    @Autowired
    private EntranceService entranceService;

    @Test
    public void queryUserDoor() {
        String openId = "YzcmCZNvbXocrsz9dm8e";
        List<DoorEntity> userDoorList = this.entranceService.queryUserDoors(openId);
        System.out.println("查询用户门禁权限：" + JSONObject.toJSONString(userDoorList));
    }

    @Test
    public void queryDoorNodes() {
        List<TreeNode> doorNodes = this.entranceService.queryHasPermissionDoorLevelNodesByOpenId("YzcmCZNvbXocrsz9dm8e");
        System.out.println("查询用户门禁权限节点信息：" + JSONObject.toJSONString(doorNodes));
    }

}
