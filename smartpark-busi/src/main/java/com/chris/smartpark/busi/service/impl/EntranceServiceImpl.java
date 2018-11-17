package com.chris.smartpark.busi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.ValidateUtils;
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
