package com.chris.smartpark.busi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.smartpark.busi.dao.DoorControllerDao;
import com.chris.smartpark.busi.dao.DoorDao;
import com.chris.smartpark.busi.dao.OpenDoorLogDao;
import com.chris.smartpark.busi.entity.DoorControllerEntity;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.entity.OpenDoorLogEntity;
import com.chris.smartpark.busi.service.EntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
    public JSONObject queryUserDoors(JSONObject paramJo) {
        JSONObject returnJo  = new JSONObject();
        try {
            //查询用户门禁权限
            Map<String,Object> params = new HashMap<String,Object>();
            Integer userId = paramJo.getInteger("userId");
            if(userId==null || userId.intValue()==0){
                returnJo.put("returnCode","0");
                returnJo.put("returnMessage","参数userId不能为空!");
                returnJo.put("returnData",new JSONObject());
                return returnJo;
            }
            params.put("userId",userId);
            //楼层id
            Integer floorId = paramJo.getInteger("floorId");
            if(floorId!=null && floorId.intValue()!=0){
                params.put("floorId",floorId);
            }
            List<DoorEntity> userDoorList=doorDao.queryUserDoor(params);
            if(userDoorList == null || userDoorList.isEmpty()){
                returnJo.put("returnCode","0");
                returnJo.put("returnMessage","未查询到数据!");
                returnJo.put("returnData",new JSONObject());
                return returnJo;
            }

            returnJo.put("returnCode","1");
            returnJo.put("returnMessage","查询成功!");
            returnJo.put("returnData",userDoorList);

        }catch (Exception e){
            returnJo.put("returnCode","0");
            returnJo.put("returnMessage","查询用户门禁权限异常"+e.getMessage());
            returnJo.put("returnData",new JSONObject());
            return returnJo;
        }

        return returnJo;
    }

    @Override
    public JSONObject openDoor(JSONObject paramJo) {
        JSONObject returnJo  = new JSONObject();
        try {
            Integer doorId = paramJo.getInteger("doorId");
            if(doorId==null || doorId.intValue()==0){
                returnJo.put("returnCode","0");
                returnJo.put("returnMessage","参数doorId不能为空!");
                returnJo.put("returnData",new JSONObject());
                return returnJo;
            }
            //1.获取门禁控制信息
            DoorControllerEntity entity = new DoorControllerEntity();
            entity.setDoorId(doorId);
            entity.setStatus("1");//1有效  0 失效
            List<DoorControllerEntity> doorControllerList=doorControllerDao.queryList(entity);

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
        return returnJo;
    }
}
