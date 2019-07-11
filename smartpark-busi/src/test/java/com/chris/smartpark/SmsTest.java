package com.chris.smartpark;

import com.alibaba.fastjson.JSONObject;
import com.chris.BusiApplication;
import com.chris.base.common.tree.TreeNode;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.Constant;
import com.chris.base.common.utils.DateUtils;
import com.chris.base.common.utils.SendSMSUtils;
import com.chris.base.common.wx.dto.WXMsgTempSendDTO;
import com.chris.base.common.wx.service.WXService;
import com.chris.base.modules.app.cache.AppLoginUser;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.sms.entity.SMSEntity;
import com.chris.smartpark.base.dto.EsbResponse;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.entity.DoorControllerEntity;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.entity.OpenDoorLogEntity;
import com.chris.smartpark.busi.entity.VisitorReservationEntity;
import com.chris.smartpark.busi.facade.EsbFacade;
import com.chris.smartpark.busi.service.EntranceService;
import com.chris.smartpark.busi.service.OpenDoorLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusiApplication.class)
public class SmsTest {
    @Autowired
    private BaseStaffService baseStaffService;

    @Autowired
    private WXService wxService;

    @Test
    public void sendApproveOKSMS() {
        String mobile = "18975841003";
        SMSEntity smsEntity = new SMSEntity();
        smsEntity.setMobile(mobile);
        smsEntity.setSmsType(Constant.SMSType.NOTICE);
        smsEntity.setTemplateCode(Constant.SMSTemplateCode.RESERVATION_SUCCESS.getTemplateCode());
        JSONObject templateParam = new JSONObject();
        templateParam.put(VisitorConstants.Keys.TIME, DateUtils.format(new Date(), "yyyy年MM月dd日 hh时mm分"));
        Map<String, Object> parkInfo = this.baseStaffService.queryParkInfoByStaffMobile(mobile);
        templateParam.put(VisitorConstants.Keys.PARK_NAME, parkInfo.get("parkName"));
        templateParam.put(VisitorConstants.Keys.BUILDING, parkInfo.get("buildingName"));
        smsEntity.setTemplateParam(templateParam.toJSONString());
        SendSMSUtils.sendSms(smsEntity);
        System.out.println("发送审核通过短信成功！变量 = " + templateParam);

        SMSEntity smsEntity2 = new SMSEntity();
        smsEntity2.setMobile(mobile);
        smsEntity2.setSmsType(Constant.SMSType.NOTICE);
        smsEntity2.setTemplateCode(Constant.SMSTemplateCode.RESERVATION_HANDLE.getTemplateCode());
        SendSMSUtils.sendSms(smsEntity2);
        System.out.println("发送预约单处理短信给员工");
    }

    @Test
    public void wxMsgSend() {
        WXMsgTempSendDTO msg = WXMsgTempSendDTO.builder().touser("obETm5c3-tUxjFf3Rgq5qRbZPRfk").template_id("tyO1NzleQYuI6uwPR0b72xN3RpBRVtKCHQ8FP4tIoTY").
                page("index").form_id("sdfsdf").build();
//        msg.setData(new JSONObject());
        CommonResponse resp = this.wxService.sendWXMsgTemp(msg, "wx2543cd2140b815b1", "2b530219c5266ffe918650556b74889c");
        System.out.println("发送微信小程序消息模板 = " + JSONObject.toJSONString(resp));
    }

}
