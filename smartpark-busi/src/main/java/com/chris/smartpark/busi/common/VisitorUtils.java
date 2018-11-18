package com.chris.smartpark.busi.common;

import com.chris.base.common.utils.SendSMSUtils;
import com.chris.base.common.utils.ValidateUtils;

public final class VisitorUtils {

    public static boolean isVerifyCodeOK(String mobile, String verifyCode, String tempCode) {
        return ValidateUtils.equals(verifyCode, SendSMSUtils.getVerifyCode4App(mobile, tempCode));
    }
}
