package com.chris.smartpark.busi.common;

public class VisitorConstants {
    public interface isSendToEntrance{
        /**
         * 送门禁开关 送
         */
        String TRUE = "true";        /**
         * 不送
         */
        String FALSE = "false";

    }
    public interface isSendNotice{
        /**
         * 是否已发送短信给受访者
         */
        int YES = 1;
        /**
         * 是否已发送短信给受访者
         */
        int NO = 0;

    }
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
        /**
         * 待审核
         */
        int PENDING_APPROVE = 1;
        /**
         * 审核不通过
         */
        int APPROVE_REJECT = 2;
        /**
         * 审核通过
         */
        int APPROVE_OK = 3;
        /**
         * 待激活
         */
        int PENDING_ACTIVE = 4;
        /**
         * 已完成
         */
        int COMPLETED = 5;
        /**
         * 已过期
         */
        int EXPIRED = 6;
    }

    /**
     * 预约单类型
     */
    public interface ReservationOrderType {
        /**
         * 线上
         */
        int ONLINE = 1;
        /**
         * 线下
         */
        int OFFLINE = 2;
    }
    /**
     * 是否为
     */
    public interface isLocalappoint {
        /**
         * 线上
         */
        int ONLINE = 0;
        /**
         * 线下
         */
        int OFFLINE = 1;
    }

    /**
     * 默认授权标识
     */
    public interface DefaultAuthorizedFlag {
        /**
         * 是
         */
        int YES = 1;
        /**
         * 否
         */
        int NO = 1;
    }

    public interface Keys {
        String OPEN_ID = "openId";
        String PAGE = "page";
        String LIMIT = "limit";
        String IDCARD_NO = "idcardNo";
        String TIME = "time";
        String PARK_NAME = "parkName";
        String REJECT_REASON = "reason";
        String RESERVATION_ORDER_ID = "reservationId";
        String TYPE = "type";
        String HOUR = "hour";
        String ORDERNO = "orderNo";

    }

    public interface Page {
        int PAGE_NO = 1;
        int PAGE_SIZE = 10;
    }
}
