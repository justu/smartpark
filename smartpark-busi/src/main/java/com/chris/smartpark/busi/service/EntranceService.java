package com.chris.smartpark.busi.service;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.tree.TreeNode;
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
     * 根据openid查询有权限的门结构数据
     * @param openId
     * @return
     */
    List<TreeNode> queryHasPermissionDoorLevelNodesByOpenId(String openId);

    /**
     * 远程开门
     */
    void remoteOpenDoor(Long doorId);
}
