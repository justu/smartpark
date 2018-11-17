package com.chris.smartpark.busi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.tree.TreeNode;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.dao.DoorControllerDao;
import com.chris.smartpark.busi.dao.DoorDao;
import com.chris.smartpark.busi.dao.OpenDoorLogDao;
import com.chris.smartpark.busi.dto.DoorLevelDTO;
import com.chris.smartpark.busi.entity.DoorControllerEntity;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.entity.OpenDoorLogEntity;
import com.chris.smartpark.busi.service.EntranceService;
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
    public List<DoorEntity>  queryUserDoors(String openId) {
        //openId 从tb_user 取mobile , 用mobile 从 base_staff 中取出员工id, 根据员工id的工位取出门禁列表
        //参数校验
        if(ValidateUtils.isEmptyString(openId)){
            throw new CommonException("参数openId不能为空!");
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("openId",openId);
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
        TreeNode root = TreeNode.createRoot(hasPermissionDoorLevelList.get(0).getBuildingId() + "", hasPermissionDoorLevelList.get(0).getBuildingName());
        treeNodes.add(root);
        treeNodes.addAll(this.buildFloorNodes(root, hasPermissionDoorLevelList));
        treeNodes.addAll(this.buildDoorNodes(hasPermissionDoorLevelList));
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
    public JSONObject openDoor(Long doorId) {
        JSONObject returnJo  = new JSONObject();
        try {
            //Integer doorId = paramJo.getLong("doorId");
            if(doorId==null || doorId.intValue()==0){
                returnJo.put("returnCode","0");
                returnJo.put("returnMessage","参数doorId不能为空!");
                returnJo.put("returnData",new JSONObject());
                return returnJo;
            }
            //1.获取门禁控制信息
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("doorId",doorId);
            params.put("status","1");//1有效  0 失效
            List<DoorControllerEntity> doorControllerList=doorControllerDao.queryDoorControllerByDoorId(params);

            if(doorControllerList == null || doorControllerList.isEmpty()){
                returnJo.put("returnCode","0");
                returnJo.put("returnMessage","未查询到门禁控制器数据数据!");
                returnJo.put("returnData",new JSONObject());
                return returnJo;
            }

            //2.远程开门 Http 考虑做成公共方法

            //3.记录开门日志,日志保存异常不影响开门
            OpenDoorLogEntity logEntity = new OpenDoorLogEntity();
            logEntity.setOpenTime(new Date());
            logEntity.setDoorId(doorId);
            logEntity.setCreateTime(new Date());
            try{
                openDoorLogDao.save(logEntity);
            }catch (Exception e){
                //日志输出异常
            }

        }catch (Exception e){
            returnJo.put("returnCode","0");
            returnJo.put("returnMessage","远程开门失败!"+e.getMessage());
            returnJo.put("returnData",new JSONObject());
            return returnJo;
        }

        returnJo.put("returnCode","1");
        returnJo.put("returnMessage","开门成功!");
        returnJo.put("returnData",new JSONObject());
        return returnJo;
    }
}
