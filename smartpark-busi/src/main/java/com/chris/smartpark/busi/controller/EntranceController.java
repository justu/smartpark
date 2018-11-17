package com.chris.smartpark.busi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.EntranceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @RequestMapping(value= "/userDoors")
    //@RequiresPermissions("busi:entrance:userDoors")
    public CommonResponse userDoors(@RequestParam String openId){
        //查询列表数据
        //Query query = new Query(params);
        List<DoorEntity> userDoorList = this.entranceService.queryUserDoors(openId);
        if(ValidateUtils.isNotEmptyCollection(userDoorList)){
            return CommonResponse.ok().setData(userDoorList);
        }else{
            return CommonResponse.error("未查询到数据!");
        }

    }

    /**
     * 远程开门
     */
    @RequestMapping("/openDoor/{doorId}")
    //@RequiresPermissions("busi:entrance:openDoor")
    public CommonResponse openDoor(@PathVariable("doorId") Long doorId){
        JSONObject paramJo = new JSONObject();
        paramJo.put("doorId",doorId);
        JSONObject returnJo=entranceService.openDoor(doorId);
        if("1".equals(returnJo.getString("returnCode"))){ //1成功 0 失败
            return CommonResponse.ok().setData(returnJo.getJSONObject("returnData"));
        }else{
            return CommonResponse.error(returnJo.getString("returnMessage")).setData(new JSONObject());
        }
    }
}
