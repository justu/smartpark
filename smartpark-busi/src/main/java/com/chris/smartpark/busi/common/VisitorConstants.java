package com.chris.smartpark.busi.common;

public class VisitorConstants {
    public interface ApproveResult {
        /**
         * 审核通过
         */
        int OK = 1;
        /**
         * 审核不通过
         */
        int REJECT = 0;
    }

    /**
     * 预约单状态
     */
    public interface ReservationOrderStatus {
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

    /**
     * 预约单类型
     */
    public interface ReservationOrderType {
        // 线上
        int ONLINE = 1;
        // 线下
        int OFFLINE = 2;
    }

    /**
     * 默认授权标识
     */
    public interface DefaultAuthorizedFlag {
        // 是
        int YES = 1;
        // 否
        int NO = 1;
    }
}
