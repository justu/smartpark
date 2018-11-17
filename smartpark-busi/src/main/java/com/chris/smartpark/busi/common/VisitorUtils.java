package com.chris.smartpark.busi.common;

import com.chris.base.common.utils.SendSMSUtils;
import com.chris.base.common.utils.ValidateUtils;

public final class VisitorUtils {

    public static boolean isVerifyCodeOK(String mobile, String verifyCode) {
        return ValidateUtils.equals(verifyCode, SendSMSUtils.getVerifyCode(mobile));
    }
}
