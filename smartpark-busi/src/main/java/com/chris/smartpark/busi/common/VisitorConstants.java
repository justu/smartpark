package com.chris.smartpark.busi.common;

public class VisitorConstants {
    public interface ApproveResult {
        /**
         * 授权通过
         */
        int AUTHORIZED = 1;
        /**
         * 未授权
         */
        int UNAUTHORIZED = 0;
    }

    /**
     * 预约单状态
     */
    public interface ReservationStatus {
        // 待审核
        int PENDING_APPROVE = 1;
        // 审核不通过
        int APPROVE_REJECT = 2;
        // 审核通过
        int APPROVE_OK = 3;
        // 待激活
        int PENDING_ACTIVE = 4;
        // 已完成
        int COMPLETED = 5;
        // 已过期
        int EXPIRED = 6;
    }
}
