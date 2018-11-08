package com.chris.smartpark.busi.service;

import com.alibaba.fastjson.JSONObject;
import com.chris.smartpark.busi.entity.DoorEntity;


/**
 * 出入管理
 */
public interface EntranceService {
    /**
     * 查询用户门禁列表
     */
    JSONObject queryUserDoors(DoorEntity doorEntity);

    /**
     * 远程开门
     */
    JSONObject openDoor(Long doorId);
}
