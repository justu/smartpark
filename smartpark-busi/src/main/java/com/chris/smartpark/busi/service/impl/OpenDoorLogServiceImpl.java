package com.chris.smartpark.busi.service.impl;

import com.chris.smartpark.busi.dao.OpenDoorLogDao;
import com.chris.smartpark.busi.entity.OpenDoorLogEntity;
import com.chris.smartpark.busi.service.OpenDoorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenDoorLogServiceImpl implements OpenDoorLogService {

    @Autowired
    private OpenDoorLogDao openDoorLogDao;

    @Override
    public void save(OpenDoorLogEntity openDoorLogEntity) {
        this.openDoorLogDao.save(openDoorLogEntity);
    }
}
