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

    /**
     * 16进制字符串转 int
     * @param str
     * @return
     */
    public static int hex2Int(String str) {
        int result = 0;
        //str.toCharArray()把String类的str转换成字符数组
        char[] hex = str.toCharArray();
        for(int i = 0; i < hex.length; i++){
            if(getHexValue(hex[i]) != -1){
                result += getHexValue(hex[i]) * Math.pow(16, hex.length-i-1);
            }
            else {
                return -1;
            }
        }
        return result;
    }

    private static int getHexValue(char ch){
        if(ch >= '0' && ch <= '9'){
            return Integer.parseInt(String.valueOf(ch));
        }
        if ( (ch >= 'a'  && ch <= 'f') || (ch >= 'A' && ch <= 'F')) {
            switch (ch) {
                case 'a':
                case 'A':
                    //这里不用break是因为执行了return以后就不会再往下执行了
                    return 10;
                case 'b':
                case 'B':
                    return 11;
                case 'c':
                case 'C':
                    return 12;
                case 'd':
                case 'D':
                    return 13;
                case 'e':
                case 'E':
                    return 14;
                case 'f':
                case 'F':
                    return 15;
            }
        }
        return -1;
    }
}
