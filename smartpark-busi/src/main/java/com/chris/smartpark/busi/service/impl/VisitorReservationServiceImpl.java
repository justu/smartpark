package com.chris.smartpark.busi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.Constant;
import com.chris.base.common.utils.DateUtils;
import com.chris.base.common.utils.SendSMSUtils;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.chris.base.modules.sms.entity.SMSEntity;
import com.chris.smartpark.base.entity.BaseStaffEntity;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.dao.AuthenticationRecordDao;
import com.chris.smartpark.busi.dao.VisitorDoorRelDao;
import com.chris.smartpark.busi.dao.VisitorReservationDao;
import com.chris.smartpark.busi.dto.AuthorizeDTO;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.*;
import com.chris.smartpark.busi.service.CarInfoService;
import com.chris.smartpark.busi.service.VisitorInfoService;
import com.chris.smartpark.busi.service.VisitorReservationService;
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

    @Override
    public ReservationDto queryObject(Long id) {
        ReservationDto reservationDto = new ReservationDto();
        //获取预约单信息
        VisitorReservationEntity visitorReservation = visitorReservationDao.queryObject(id);
        BeanUtil.copyProperties(visitorReservation, reservationDto);
        //访客
        VisitorInfoEntity visitorInfoEntity = visitorInfoService.queryObject(visitorReservation.getVisitorId());
        reservationDto.setName(visitorInfoEntity.getName());
        reservationDto.setIdcardNo(visitorInfoEntity.getIdcardNo());
        //受访者
        BaseStaffEntity baseStaffEntity = baseStaffService.queryObject(visitorReservation.getStaffId());
        reservationDto.setStaffId(baseStaffEntity.getId());
        reservationDto.setStaffName(baseStaffEntity.getName());
        reservationDto.setStaffPhone(baseStaffEntity.getMobile());
        //获取同行车辆信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reservationId", id);
        List<CarInfoEntity> list = carInfoService.queryList(map);
        reservationDto.setCarInfoEntitys(list);
        return reservationDto;
    }

    @Override
    public List<VisitorReservationEntity> queryList(Map<String, Object> map) {
        return visitorReservationDao.queryList(map);
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

    @Override
    public VisitorReservationEntity queryObjectById(Long id) {
        return visitorReservationDao.queryObject(id);
    }

    /**
     * 访客预约审核
     * @param authorizeDTO
     * @return
     */
    @Override
    public void approve(AuthorizeDTO authorizeDTO) {
        log.error("请求参数：{}", JSONObject.toJSONString(authorizeDTO));
        this.validateParams(authorizeDTO);
        //1.查询预约单
        VisitorReservationEntity visitorReservationOrder = visitorReservationDao.queryObject(authorizeDTO.getReservationId());
        if (ValidateUtils.notEquals(VisitorConstants.ReservationStatus.PENDING_APPROVE + "", visitorReservationOrder.getStatus())) {
            //不是待审核状态无需处理
            throw new CommonException("当前预约单已经审核，请查看预约单详情！");
        }
        // 根据访客ID查询访客信息
        VisitorInfoEntity visitorInfo = this.visitorInfoService.queryObject(visitorReservationOrder.getVisitorId());
        // 根据openId查询用户信息
        UserEntity user = this.userService.queryUserByOpenId(authorizeDTO.getOpenId());
        // 审核不通过处理
        if (ValidateUtils.equals(VisitorConstants.ApproveResult.REJECT, authorizeDTO.getApproveReslut())) {
            //更新预约单状态为审核不通过
            visitorReservationOrder.setStatus(VisitorConstants.ReservationStatus.APPROVE_REJECT + "");
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

            List<CarInfoEntity> carInfoList = this.carInfoService.queryList(ImmutableMap.of("reservationId", visitorReservationOrder.getId()));
            if (ValidateUtils.isNotEmptyCollection(carInfoList)) {
                // 存在车辆信息则需要送车管系统
                // TODO 送车管系统（第一阶段暂不实现）
                log.error("送车管系统开始......");
            }
            if (VisitorConstants.ReservationType.OFFLINE == visitorReservationOrder.getType()) {
                // 线下预约的预约单需要送门禁
                // TODO 调用李森写好的服务
            }
            //4.更新预约信息
            visitorReservationOrder.setActStartTime(authorizeDTO.getActStartTime());
            visitorReservationOrder.setActEndTime(authorizeDTO.getActEndTime());
            visitorReservationOrder.setStatus(VisitorConstants.ReservationStatus.APPROVE_OK + "");
            this.visitorReservationDao.update(visitorReservationOrder);
            //5.发送审核成功短信给访客
            this.sendApproveOKSMS(authorizeDTO, visitorInfo, user);
        }
        this.saveAuthenticationRecord(authorizeDTO, visitorReservationOrder, user);
        log.error("授权结束......");
    }

    /**
     * 保存授权结果记录
     * @param authorizeDTO
     * @param visitorReservationOrder
     */
    private void saveAuthenticationRecord(AuthorizeDTO authorizeDTO, VisitorReservationEntity visitorReservationOrder, UserEntity user) {
        AuthenticationRecordEntity authenticationRecord = new AuthenticationRecordEntity();
        authenticationRecord.setReservationId(visitorReservationOrder.getId());
        authenticationRecord.setVisitorId(visitorReservationOrder.getVisitorId());
        authenticationRecord.setApplyBeginTime(visitorReservationOrder.getAppointEndTime());
        authenticationRecord.setApplyEndTime(visitorReservationOrder.getAppointEndTime());
        if (ValidateUtils.equals(authorizeDTO.getApproveReslut(), VisitorConstants.ApproveResult.OK)) {
            authenticationRecord.setAuthBeginTime(authorizeDTO.getActStartTime());
            authenticationRecord.setAuthEndTime(authorizeDTO.getActEndTime());
            authenticationRecord.setStatus(VisitorConstants.ApproveResult.OK + "");
        } else {
            authenticationRecord.setStatus(VisitorConstants.ApproveResult.REJECT + "");
        }
        authenticationRecord.setCreateUserId(user.getUserId());
        authenticationRecord.setCreateTime(new Date());
        authenticationRecord.setUpdateUserId(user.getUserId());
        authenticationRecord.setUpdateTime(new Date());
        authenticationRecordDao.save(authenticationRecord);
    }

    private void saveVisitorDoorRelation(AuthorizeDTO authorizeDTO, VisitorReservationEntity visitorReservation) {
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

    private void sendApproveOKSMS(AuthorizeDTO authorizeDTO, VisitorInfoEntity visitorInfo, UserEntity user) {
        SMSEntity smsEntity = new SMSEntity();
        smsEntity.setMobile(visitorInfo.getPhone());
        smsEntity.setSmsType(Constant.SMSType.NOTICE);
        smsEntity.setTemplateCode(Constant.SMSTemplateCode.RESERVATION_SUCCESS.getTemplateCode());
        JSONObject templateParam = new JSONObject();
        templateParam.put("time", DateUtils.format(authorizeDTO.getActStartTime(), "yyyy年MM月dd日 hh时mm分"));
        // TODO 先写死
        templateParam.put("parkName", "信息园");
        smsEntity.setTemplateParam(templateParam.toJSONString());
        SendSMSUtils.sendSms(smsEntity);
    }

    private void sendApproveRejectSMS(AuthorizeDTO authorizeDTO, VisitorInfoEntity visitorInfo) {
        SMSEntity smsEntity = new SMSEntity();
        smsEntity.setMobile(visitorInfo.getPhone());
        smsEntity.setSmsType(Constant.SMSType.NOTICE);
        smsEntity.setTemplateCode(Constant.SMSTemplateCode.RESERVATION_FAIL.getTemplateCode());
        JSONObject templateParam = new JSONObject();
        templateParam.put("reason", authorizeDTO.getRejectReaon());
        smsEntity.setTemplateParam(templateParam.toJSONString());
        SendSMSUtils.sendSms(smsEntity);
    }

    /**
     * 参数校验
     *
     * @param authorizeDTO
     */
    private void validateParams(AuthorizeDTO authorizeDTO) {
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

}
