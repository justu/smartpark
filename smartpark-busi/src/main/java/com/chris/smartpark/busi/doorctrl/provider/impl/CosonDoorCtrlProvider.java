package com.chris.smartpark.busi.doorctrl.provider.impl;

import com.chris.base.common.exception.CommonException;
import com.chris.smartpark.busi.common.VisitorUtils;
import com.chris.smartpark.busi.doorctrl.provider.DoorCtrlProvider;
import org.springframework.stereotype.Service;

@Service("cosonDoorCtrlProvider")
public class CosonDoorCtrlProvider implements DoorCtrlProvider {
    @Override
    public int convertPhyCardId(String physicalCardId) {
        if (physicalCardId.length() < 10) {
            throw new CommonException("物理卡ID位数小于10位");
        }
        String cardId = physicalCardId.substring(10);
        int resultValue = VisitorUtils.hex2Int(cardId);
        return resultValue;
    }
}
