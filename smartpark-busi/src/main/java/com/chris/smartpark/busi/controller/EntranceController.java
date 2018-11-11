package com.chris.smartpark.busi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.utils.CommonResponse;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.EntranceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 出入管理
 */
@RestController
@RequestMapping("/busi/entrance")
public class EntranceController {
    @Autowired
    EntranceService entranceService;

    /**
     * 用户门禁权限列表
     */
    @RequestMapping(value= "/userDoors" , method = RequestMethod.POST)
    //@RequiresPermissions("busi:entrance:userDoors")
    public CommonResponse userDoors(@RequestBody DoorEntity doorEntity){
        //查询列表数据
        //Query query = new Query(params);
        JSONObject returnJo=entranceService.queryUserDoors(doorEntity);
        if("1".equals(returnJo.getString("returnCode"))){
            return CommonResponse.ok().setData( returnJo.getJSONArray("returnData"));
        }else{
            return CommonResponse.error(returnJo.getString("returnMessage")).setData(new JSONArray());
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
