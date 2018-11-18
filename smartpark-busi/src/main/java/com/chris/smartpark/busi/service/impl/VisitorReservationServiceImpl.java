package com.chris.smartpark.busi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.*;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.chris.base.modules.sms.entity.SMSEntity;
import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.common.VisitorUtils;
import com.chris.smartpark.busi.dao.AuthenticationRecordDao;
import com.chris.smartpark.busi.dao.VisitorDoorRelDao;
import com.chris.smartpark.busi.dao.VisitorReservationDao;
import com.chris.smartpark.busi.dto.ReservationOrderApproveDTO;
import com.chris.smartpark.busi.dto.ReservationOrderDTO;
import com.chris.smartpark.busi.dto.ReservationOrderQryDTO;
import com.chris.smartpark.busi.entity.*;
import com.chris.smartpark.busi.service.*;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("visitorReservationService")
public class VisitorReservationServiceImpl implements VisitorReservationService {
    @Autowired
    private VisitorReservationDao visitorReservationDao;
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private VisitorInfoService visitorInfoService;
    @Autowired
    private BaseStaffService baseStaffService;
    @Autowired
    private VisitorDoorRelDao visitorDoorRelDao;
    @Autowired
    private AuthenticationRecordDao authenticationRecordDao;
    @Autowired
    private UserService userService;
    @Autowired
    private VisitorIdcardService visitorIdcardService;
    @Autowired
    private VisitorInfoHisService visitorInfoHisService;

    /**
     * 查询预约单明细
     * @param id
     * @return
     */
    @Override
    public ReservationOrderDTO queryReservationOrderDetail(Long id) {
        ReservationOrderDTO reservationOrderDTO = new ReservationOrderDTO();
        //获取预约单信息
        VisitorReservationEntity visitorReservation = this.visitorReservationDao.queryObject(id);
        BeanUtil.copyProperties(visitorReservation, reservationOrderDTO);
        //访客
        VisitorInfoEntity visitorInfoEntity = this.visitorInfoService.queryObject(visitorReservation.getVisitorId());
        reservationOrderDTO.setName(visitorInfoEntity.getName());
        reservationOrderDTO.setIdcardNo(visitorInfoEntity.getIdcardNo());
        //受访者
        BaseStaffEntity baseStaffEntity = this.baseStaffService.queryObject(visitorReservation.getStaffId());
        reservationOrderDTO.setStaffId(baseStaffEntity.getId());
        reservationOrderDTO.setStaffName(baseStaffEntity.getUsername());
        reservationOrderDTO.setStaffPhone(baseStaffEntity.getMobile());
        //获取同行车辆信息
        List<CarInfoEntity> list = this.carInfoService.queryList(ImmutableMap.of(VisitorConstants.Keys.RESERVATION_ORDER_ID, id));
        reservationOrderDTO.setCarInfoEntitys(list);
        return reservationOrderDTO;
    }

    @Override
    public List<VisitorReservationEntity> queryList(Map<String, Object> map) {
        return visitorReservationDao.queryList(map);
    }

    @Override
    public PageUtils queryReservationOrdersByOpenId(Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ReservationOrderQryDTO> resultList = this.visitorReservationDao.queryReservationOrdersByOpenId(query);
        int total = this.visitorReservationDao.countReservationOrdersByOpenId(query);
        PageUtils pageUtil = new PageUtils(resultList, total, query.getLimit(), query.getPage());
        return pageUtil;
    }

    @Override
    public List<VisitorReservationEntity> queryEffectRecord(String idcardNo) {
        return visitorReservationDao.queryEffectRecord(idcardNo);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return visitorReservationDao.queryTotal(map);
    }

    @Override
    public void save(VisitorReservationEntity visitorReservation) {
        visitorReservationDao.save(visitorReservation);
    }

    @Override
    public void update(VisitorReservationEntity visitorReservation) {
        visitorReservationDao.update(visitorReservation);
    }

    @Override
    public void delete(Long id) {
        visitorReservationDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        visitorReservationDao.deleteBatch(ids);
    }

    /**
     * 生成预约单信息
     */
    @Override
    public void createReservationOrder(ReservationOrderDTO reservationOrderDTO) {
        this.validateReservationOrder(reservationOrderDTO);
        // TODO 查询系统配置判断是否配置了同行人详细信息(需要抽掉一个字典查询公共方法) 暂时放到第二阶段
        //1.保存访客信息（历史信息）
        VisitorInfoEntity visitorInfo = new VisitorInfoEntity();
        visitorInfo.setIdcardNo(reservationOrderDTO.getIdcardNo());
        VisitorInfoEntity rcd = this.visitorInfoService.selectByIdcardNo(visitorInfo);
        //存在身份证信息则保存
        if (ValidateUtils.isNotEmpty(reservationOrderDTO.getVisitorIdcardEntity()) &&
                ValidateUtils.isNotEmptyString(reservationOrderDTO.getIdcardNo())) {
            this.visitorIdcardService.save(reservationOrderDTO.getVisitorIdcardEntity());
        }
        if (ValidateUtils.isNotEmpty(rcd)) {
            //删除现有记录并记录到历史记录表
            VisitorInfoHisEntity visitorInfoHis = new VisitorInfoHisEntity();
            BeanUtil.copyProperties(rcd, visitorInfoHis);
            visitorInfoHis.setCreateTime(DateUtils.currentDate());
            visitorInfoHis.setVisitorId(rcd.getId());
            visitorInfoHis.setId(null);
            this.visitorInfoHisService.save(visitorInfoHis);
            this.visitorInfoService.delete(rcd.getId());
            visitorInfo.setId(rcd.getId());
        }
        visitorInfo.setName(reservationOrderDTO.getName());
        visitorInfo.setPhone(reservationOrderDTO.getPhone());
        visitorInfo.setCreateTime(DateUtils.currentDate());
        this.visitorInfoService.save(visitorInfo);//此处可获取到visitorid = visitorInfo.getId()
        //2.保存预约单信息
        VisitorReservationEntity reservationOrder = new VisitorReservationEntity();
        BeanUtil.copyProperties(reservationOrderDTO, reservationOrder);
        reservationOrder.setStaffMobile(reservationOrderDTO.getStaffPhone());
        reservationOrder.setType(VisitorConstants.ReservationOrderType.ONLINE);
        reservationOrder.setVisitorId(visitorInfo.getId());
        reservationOrder.setCreateTime(DateUtils.currentDate());
        reservationOrder.setStatus(VisitorConstants.ReservationOrderStatus.PENDING_APPROVE + "");
        this.save(reservationOrder);
        //3.保存车牌信息
        if (ValidateUtils.isNotEmptyCollection(reservationOrderDTO.getCarInfoEntitys())) {
            for (CarInfoEntity carInfo : reservationOrderDTO.getCarInfoEntitys()) {
                carInfo.setCreateTime(DateUtils.currentDate());
                carInfo.setReservationId(reservationOrder.getId());
            }
            this.carInfoService.batchInsert(reservationOrderDTO.getCarInfoEntitys());
        }
        // TODO 4.保存同行人信息（暂缓）
    }

    /**
     * 校验预约单
     * @param reservationOrderDTO
     */
    private void validateReservationOrder(ReservationOrderDTO reservationOrderDTO) {
        //校验预约结束时间结束时间在开始时间一小时以后
        if(!this.isOneHourAfter(reservationOrderDTO.getAppointStartTime(),reservationOrderDTO.getAppointEndTime())){
            throw new CommonException("预约结束时间需要在开始时间一小时以后！");
        }
        //校验身份证是否合法
        if (!CommonValidator.isIDCard(reservationOrderDTO.getIdcardNo())) {
            throw new CommonException("身份证格式不正确！");
        }
        //校验手机号是否正确
        if (!CommonValidator.isMobile(reservationOrderDTO.getPhone())) {
            throw new CommonException("手机号格式不正确！");
        }
        //校验验证码是否正确
        if (!VisitorUtils.isVerifyCodeOK(reservationOrderDTO.getPhone(), reservationOrderDTO.getVerifyCode(), Constant.SMSTemplateCode.RESERVATION_VERIFY_CODE.getTemplateCode())) {
            throw new CommonException("验证码不正确！");
        }
        //校验员工手机号是否存在
        if (ValidateUtils.isEmpty(this.baseStaffService.queryByMobile(reservationOrderDTO.getStaffPhone()))) {
            throw new CommonException("被访人信息不存在！");
        }
    }

    /**
     * 身份鉴权送门禁
     */
    @Override
    public void checkIdCardAndGetAuth(VisitorIdcardEntity visitorIdcard) {
        log.error("访客身份信息验证请求JSON {}", JSONObject.toJSONString(visitorIdcard));
        //验证有无有效预约单
        List<VisitorReservationEntity> reservationOrders = this.queryByIdcardAndStatus(visitorIdcard.getIdcardNo(), VisitorConstants.ReservationOrderStatus.APPROVE_OK + "");
        if (ValidateUtils.isEmptyCollection(reservationOrders)) {
            throw new CommonException("该身份证没有匹配的预约单信息");
        } else {
            for (VisitorReservationEntity reservationOrder : reservationOrders) {
                visitorIdcard.setVisitorId(reservationOrder.getVisitorId());
                //保存或更新信息到访客身份信息表
                List<VisitorIdcardEntity> idcardList = this.visitorIdcardService.queryList(ImmutableMap.of(VisitorConstants.Keys.IDCARD_NO, visitorIdcard.getIdcardNo()));
                if (ValidateUtils.isNotEmptyCollection(idcardList)) {
                    this.visitorIdcardService.save(visitorIdcard);
                } else {
                    //更新访客身份信息，一般更新的情况很少
                    visitorIdcard.setId(idcardList.get(0).getId());
                    this.visitorIdcardService.update(visitorIdcard);
                }
                //组装入参调用门禁接口授权
                Date startTime = reservationOrder.getAppointStartTime();
                Date endTime = reservationOrder.getAppointEndTime();
                // TODO 需要根据访客ID查询访客对应可授权的门禁列表
                log.info("========调用门禁接口授权成功=====");
                //更新预约单状态为完成
                reservationOrder.setStatus(VisitorConstants.ReservationOrderStatus.COMPLETED + "");
                this.visitorReservationDao.updateStatus(reservationOrder);
            }
        }
    }

    ;

    @Override
    public VisitorReservationEntity queryObjectById(Long id) {
        return visitorReservationDao.queryObject(id);
    }

    /**
     * 访客预约审核
     *
     * @param authorizeDTO
     * @return
     */
    @Override
    public void approve(ReservationOrderApproveDTO authorizeDTO) {
        log.error("请求参数：{}", JSONObject.toJSONString(authorizeDTO));
        this.validateParams(authorizeDTO);
        //1.查询预约单
        VisitorReservationEntity visitorReservationOrder = visitorReservationDao.queryObject(authorizeDTO.getReservationId());
        if (ValidateUtils.notEquals(VisitorConstants.ReservationOrderStatus.PENDING_APPROVE + "", visitorReservationOrder.getStatus())) {
            //不是待审核状态无需处理
            throw new CommonException("当前预约单已经审核，请查看预约单详情！");
        }
        // 根据访客ID查询访客信息
        VisitorInfoEntity visitorInfo = this.visitorInfoService.queryObject(visitorReservationOrder.getVisitorId());
        // 根据openId查询用户信息(员工或管理员)
        UserEntity user = this.userService.queryUserByOpenId(authorizeDTO.getOpenId());
        // 审核不通过处理
        if (ValidateUtils.equals(VisitorConstants.ApproveResult.REJECT, authorizeDTO.getApproveReslut())) {
            //更新预约单状态为审核不通过
            visitorReservationOrder.setStatus(VisitorConstants.ReservationOrderStatus.APPROVE_REJECT + "");
            this.visitorReservationDao.updateStatus(visitorReservationOrder);
            //发送审核结果短信给访客
            this.sendApproveRejectSMS(authorizeDTO, visitorInfo);
        } else if (ValidateUtils.equals(VisitorConstants.ApproveResult.OK, authorizeDTO.getApproveReslut())) {
            /**
             * 授权审核通过处理
             * 1、保存访客门禁关系
             * 2、同步信息到人脸识别
             * 3、同步信息到车管系统（如果有车辆信息）
             * 4、如果是线下预约，则需要将门禁信息送门禁系统
             */
            this.saveVisitorDoorRelation(authorizeDTO, visitorReservationOrder);
            // TODO 送人脸识别系统（第一阶段暂不实现）
            log.error("送人脸识别系统");

            List<CarInfoEntity> carInfoList = this.carInfoService.queryList(ImmutableMap.of(VisitorConstants.Keys.RESERVATION_ORDER_ID, visitorReservationOrder.getId()));
            if (ValidateUtils.isNotEmptyCollection(carInfoList)) {
                // 存在车辆信息则需要送车管系统
                // TODO 送车管系统（第一阶段暂不实现）
                log.error("送车管系统开始......");
            }
            if (VisitorConstants.ReservationOrderType.OFFLINE == visitorReservationOrder.getType()) {
                // 线下预约的预约单需要送门禁
                // TODO 调用李森写好的服务
            }
            //4.更新预约信息
            visitorReservationOrder.setActStartTime(authorizeDTO.getActStartTime());
            visitorReservationOrder.setActEndTime(authorizeDTO.getActEndTime());
            visitorReservationOrder.setStatus(VisitorConstants.ReservationOrderStatus.APPROVE_OK + "");
            this.visitorReservationDao.update(visitorReservationOrder);
            //5.发送审核成功短信给访客
            this.sendApproveOKSMS(authorizeDTO, visitorInfo, user);
        }
        this.saveAuthenticationRecord(authorizeDTO, visitorReservationOrder, user);
        log.error("授权结束......");
    }

    /**
     * 保存授权结果记录
     *
     * @param authorizeDTO
     * @param visitorReservationOrder
     */
    private void saveAuthenticationRecord(ReservationOrderApproveDTO authorizeDTO, VisitorReservationEntity visitorReservationOrder, UserEntity user) {
        AuthenticationRecordEntity authenticationRecord = new AuthenticationRecordEntity();
        authenticationRecord.setReservationId(visitorReservationOrder.getId());
        authenticationRecord.setVisitorId(visitorReservationOrder.getVisitorId());
        authenticationRecord.setApplyBeginTime(visitorReservationOrder.getAppointEndTime());
        authenticationRecord.setApplyEndTime(visitorReservationOrder.getAppointEndTime());
        if (ValidateUtils.equals(authorizeDTO.getApproveReslut(), VisitorConstants.ApproveResult.OK)) {
            authenticationRecord.setAuthBeginTime(authorizeDTO.getActStartTime());
            authenticationRecord.setAuthEndTime(authorizeDTO.getActEndTime());
            authenticationRecord.setStatus(VisitorConstants.ApproveResult.OK + "");
            authenticationRecord.setAuthDesc("审核通过");
        } else {
            authenticationRecord.setStatus(VisitorConstants.ApproveResult.REJECT + "");
            authenticationRecord.setAuthDesc(authorizeDTO.getRejectReaon());
        }
        authenticationRecord.setAuthTime(new Date());
        authenticationRecord.setCreateUserId(user.getUserId());
        authenticationRecord.setCreateTime(new Date());
        authenticationRecord.setUpdateUserId(user.getUserId());
        authenticationRecord.setUpdateTime(new Date());
        authenticationRecordDao.save(authenticationRecord);
    }

    private void saveVisitorDoorRelation(ReservationOrderApproveDTO authorizeDTO, VisitorReservationEntity visitorReservation) {
        List<VisitorDoorRelEntity> visitorDoorRelList = new ArrayList<VisitorDoorRelEntity>();
        for (Long doorId : authorizeDTO.getDoorList()) {
            VisitorDoorRelEntity visitorDoorRelEntity = new VisitorDoorRelEntity();
            visitorDoorRelEntity.setReservationId(authorizeDTO.getReservationId());
            visitorDoorRelEntity.setDoorId(doorId);
            visitorDoorRelEntity.setVisitorId(visitorReservation.getVisitorId());
            visitorDoorRelEntity.setStatus("1");
            visitorDoorRelList.add(visitorDoorRelEntity);
        }
        visitorDoorRelDao.saveBatch(visitorDoorRelList);
    }

    private void sendApproveOKSMS(ReservationOrderApproveDTO authorizeDTO, VisitorInfoEntity visitorInfo, UserEntity user) {
        SMSEntity smsEntity = new SMSEntity();
        smsEntity.setMobile(visitorInfo.getPhone());
        smsEntity.setSmsType(Constant.SMSType.NOTICE);
        smsEntity.setTemplateCode(Constant.SMSTemplateCode.RESERVATION_SUCCESS.getTemplateCode());
        JSONObject templateParam = new JSONObject();
        templateParam.put(VisitorConstants.Keys.TIME, DateUtils.format(authorizeDTO.getActStartTime(), "yyyy年MM月dd日 hh时mm分"));
        // TODO 先写死
        templateParam.put(VisitorConstants.Keys.PARK_NAME, this.getParkNameByUserMobile(user.getMobile()));
        smsEntity.setTemplateParam(templateParam.toJSONString());
        SendSMSUtils.sendSms(smsEntity);
    }

    private String getParkNameByUserMobile(String mobile) {
        return this.baseStaffService.queryParkNameByStaffMobile(mobile);
    }

    private void sendApproveRejectSMS(ReservationOrderApproveDTO authorizeDTO, VisitorInfoEntity visitorInfo) {
        SMSEntity smsEntity = new SMSEntity();
        smsEntity.setMobile(visitorInfo.getPhone());
        smsEntity.setSmsType(Constant.SMSType.NOTICE);
        smsEntity.setTemplateCode(Constant.SMSTemplateCode.RESERVATION_FAIL.getTemplateCode());
        JSONObject templateParam = new JSONObject();
        templateParam.put(VisitorConstants.Keys.REJECT_REASON, authorizeDTO.getRejectReaon());
        smsEntity.setTemplateParam(templateParam.toJSONString());
        SendSMSUtils.sendSms(smsEntity);
    }

    /**
     * 参数校验
     *
     * @param authorizeDTO
     */
    private void validateParams(ReservationOrderApproveDTO authorizeDTO) {
        if (ValidateUtils.isEmpty(authorizeDTO.getReservationId())) {
            throw new CommonException("预约单为空！");
        }
        if (ValidateUtils.isEmptyString(authorizeDTO.getOpenId())) {
            throw new CommonException("openid为空！");
        }
        if (VisitorConstants.ApproveResult.OK == authorizeDTO.getApproveReslut()) {
            // 授权通过则需要传入门禁列表ID
            if (ValidateUtils.isEmptyCollection(authorizeDTO.getDoorList())) {
                throw new CommonException("授权门禁列表为空！");
            }
        }
        if (VisitorConstants.ApproveResult.REJECT == authorizeDTO.getApproveReslut()) {
            // 未授权则需要填写拒绝原因
            if (ValidateUtils.isEmptyString(authorizeDTO.getRejectReaon())) {
                throw new CommonException("拒绝原因为空！");
            }
        }
    }

    @Override
    public List<VisitorReservationEntity> queryByIdcardAndStatus(String idcardNo, String status) {
        return this.visitorReservationDao.queryByIdcardAndStatus(idcardNo, status);
    }

    private boolean isOneHourAfter(Date actStartTime,Date actEndTime){
        long interval = (actEndTime.getTime() - actStartTime.getTime())/1000 - 3600;
        if(interval<0){
           return false;
        }
        return true;
    }
}
