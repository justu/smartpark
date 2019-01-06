package com.chris.smartpark.busi.service.impl;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.tree.TreeNode;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.smartpark.busi.common.DoorControllerProcessor;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.dao.DoorControllerDao;
import com.chris.smartpark.busi.dao.DoorDao;
import com.chris.smartpark.busi.dao.OpenDoorLogDao;
import com.chris.smartpark.busi.dto.DoorLevelDTO;
import com.chris.smartpark.busi.entity.DoorControllerEntity;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.EntranceService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("entranceService")
public class EntranceServiceImpl implements EntranceService {
    //日志处理

    @Autowired
    private DoorDao doorDao;

    @Autowired
    private DoorControllerDao doorControllerDao;

    @Autowired
    private OpenDoorLogDao openDoorLogDao;

    @Override
    public List<DoorEntity> queryUserDoors(String openId) {
        //openId 从tb_user 取mobile , 用mobile 从 base_staff 中取出员工id, 根据员工id的工位取出门禁列表
        //参数校验
        if (ValidateUtils.isEmptyString(openId)) {
            throw new CommonException("参数openId不能为空!");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VisitorConstants.Keys.OPEN_ID, openId);
        return this.doorDao.queryUserDoor(params);
    }

    @Override
    public List<TreeNode> queryHasPermissionDoorLevelNodesByOpenId(String openId) {
        // 查询员工有权限的门信息
        List<DoorLevelDTO> hasPermissionDoorLevelList = this.doorDao.queryHasPermissionDoorsByOpenId(openId);
        if (ValidateUtils.isEmptyCollection(hasPermissionDoorLevelList)) {
            throw new CommonException("当前员工没有门禁权限，不能授权！");
        }
        // 组装成树结构，用于前台展示
        // 根节点为楼宇名称
        List<TreeNode> treeNodes = Lists.newArrayList();
        TreeNode root = TreeNode.createRoot("0", hasPermissionDoorLevelList.get(0).getBuildingName());
        treeNodes.add(root);
        root.setChildren(this.buildFloorNodes(root, hasPermissionDoorLevelList));
        List<TreeNode> doorNodes = this.buildDoorNodes(hasPermissionDoorLevelList);
        root.getChildren().forEach(floorNode -> {
            List<TreeNode> chidrenNodes = doorNodes.stream().filter(doorNode -> ValidateUtils.equals(doorNode.getParentNodeId(), floorNode.getNodeId())).collect(Collectors.toList());
            floorNode.getChildren().addAll(chidrenNodes);
        });
        return treeNodes;
    }

    private List<TreeNode> buildFloorNodes(TreeNode root, List<DoorLevelDTO> hasPermissionDoorLevelList) {
        Map<Long, List<DoorLevelDTO>> groupFloorMap = hasPermissionDoorLevelList.stream().collect(Collectors.groupingBy(DoorLevelDTO::getFloorId));
        List<TreeNode> floorNodes = new ArrayList<>(groupFloorMap.size());
        groupFloorMap.forEach((k, v) -> {
            TreeNode floorNode = TreeNode.createNode(k + "", v.get(0).getFloorName(), root.getNodeId(), null);
            floorNodes.add(floorNode);
        });
        return floorNodes;
    }

    private List<TreeNode> buildDoorNodes(List<DoorLevelDTO> hasPermissionDoorLevelList) {
        return hasPermissionDoorLevelList.stream().map(item -> {
            TreeNode doorNode = TreeNode.createNode(item.getDoorId() + "", item.getDoorName(), item.getFloorId() + "",
                    VisitorConstants.DefaultAuthorizedFlag.YES == item.getDefaultAuthorizedFlag() ? VisitorConstants.DefaultAuthorizedFlag.YES + "" : null);
            return doorNode;
        }).collect(Collectors.toList());
    }

    @Override
    public void remoteOpenDoor(Map<String, Object> params) {
        if (ValidateUtils.isEmpty(params)) {
            throw new CommonException("请求参数不能为空！");
        }
        if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.OPEN_ID))) {
            throw new CommonException("openid不能为空！");
        }
        if (ValidateUtils.isEmpty(params.get(VisitorConstants.Keys.DOOR_ID))) {
            throw new CommonException("门ID不能为空！");
        }
        // 首先校验员工是否有对应门的权限
        Long doorId = Long.valueOf(params.get(VisitorConstants.Keys.DOOR_ID).toString());
        String openId = params.get(VisitorConstants.Keys.OPEN_ID).toString();
        // 根据门ID查询其对应的门禁控制器
        List<DoorControllerEntity> doorControllers = doorControllerDao.queryDoorControllerByDoorId(
                ImmutableMap.of(VisitorConstants.Keys.DOOR_ID, doorId, VisitorConstants.Keys.OPEN_ID, openId));
        if (ValidateUtils.isEmptyCollection(doorControllers)) {
            throw new CommonException("当前员工没有该门禁操作权限");
        }
        DoorControllerEntity doorController = doorControllers.get(0);
        DoorControllerProcessor.remoteOpenDoor(doorController);
    }
}
