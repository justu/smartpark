package com.chris.smartpark.busi.controller;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.tree.TreeNode;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.EntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出入管理
 */
@RestController
@RequestMapping("/app/entrance")
public class EntranceController {
    @Autowired
    EntranceService entranceService;

    /**
     * 用户门禁权限列表
     */
    @RequestMapping(value = "/userDoors")
    //@RequiresPermissions("busi:entrance:userDoors")
    public CommonResponse userDoors(@RequestParam String openId) {
        //查询列表数据
        //Query query = new Query(params);
        List<DoorEntity> userDoorList = this.entranceService.queryUserDoors(openId);
        if (ValidateUtils.isNotEmptyCollection(userDoorList)) {
            return CommonResponse.ok().setData(userDoorList);
        } else {
            return CommonResponse.error("未查询到数据!");
        }
    }

    /**
     * 根据openId查询用户有权限门节点信息
     *
     * @param openId
     * @return
     */
    @GetMapping("/queryDoorNodes")
//    @Login
    public CommonResponse queryDoorNodes(String openId) {
        List<TreeNode> doorNodes = this.entranceService.queryHasPermissionDoorLevelNodesByOpenId(openId);
        return CommonResponse.ok().setData(doorNodes);
    }

    /**
     * 远程开门
     */
    @GetMapping("/remoteOpenDoor")
    public CommonResponse remoteOpenDoor(Long doorId) {
        this.entranceService.remoteOpenDoor(doorId);
        return CommonResponse.ok();
    }
}
