package com.chris.smartpark.busi.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 出入管理
 */
public interface EntranceService {
    /**
     * 查询用户门禁列表
     */
    JSONObject queryUserDoors(JSONObject paramJo);

    /**
     * 远程开门
     */
    JSONObject openDoor(JSONObject paramJo);
}
