package com.chris.smartpark.busi.common;

import com.chris.base.common.utils.DateUtils;
import com.chris.base.common.utils.SendSMSUtils;
import com.chris.base.common.utils.ValidateUtils;

public final class VisitorUtils {

    public static boolean isVerifyCodeOK(String mobile, String verifyCode, String tempCode) {
        return ValidateUtils.equals(verifyCode, SendSMSUtils.getVerifyCode4App(mobile, tempCode));
    }
    public static String getReservationNo(){
        StringBuilder sb = new StringBuilder();
        sb.append("YYD").append(DateUtils.currentDateStr("yyyyMMddHHmmss"));
        return  sb.toString();
    }
}
