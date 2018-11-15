package com.chris.smartpark.busi.service;

import com.alibaba.fastjson.JSONObject;
import com.chris.smartpark.busi.entity.DoorEntity;

import java.util.List;


/**
 * 出入管理
 */
public interface EntranceService {
    /**
     * 查询用户门禁列表
     */
    List<DoorEntity> queryUserDoors(String openId);

    /**
     * 远程开门
     */
    JSONObject openDoor(Long doorId);
}
