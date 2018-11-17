package com.chris.smartpark;
import com.chris.base.common.utils.*;
import com.chris.smartpark.base.dto.BaseStaffDTO;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.entity.CarInfoEntity;
import com.chris.smartpark.busi.entity.DoorEntity;
import com.chris.smartpark.busi.service.EntranceService;
import com.google.common.collect.Lists;
import com.chris.smartpark.busi.entity.VisitorIdcardEntity;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.chris.BusiApplication;
import com.chris.smartpark.busi.dto.ReservationDto;
import com.chris.smartpark.busi.entity.VisitorInfoEntity;
import com.chris.smartpark.busi.service.VisitorInfoService;
import com.chris.smartpark.busi.service.VisitorReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusiApplication.class)
public class VisitorTest {
	@Autowired
	private VisitorInfoService visitorInfoService;

	@Autowired
	private VisitorReservationService visitorReservationService;

	@Autowired
	private BaseStaffService baseStaffService;

	@Autowired
	private EntranceService entranceService;

	@Test
	public void queryVisitorInfo() {
		VisitorInfoEntity visitorInfo = this.visitorInfoService.queryObject(Long.parseLong("1"));
		System.out.println("查询访客信息：" + JSONObject.toJSONString(visitorInfo));
	}

    /**
     * 查询预约单
     */
    @Test
	public void queryReservationOrder() {
        ReservationDto reservationOrder = this.visitorReservationService.queryObject(3L);
        System.out.println("预约单JSON = " + JSONObject.toJSONString(reservationOrder));
    }

    /**
     * 创建预约单
     */
	@Test
	public void createReservationOrder() {
        ReservationDto reservationOrder = new ReservationDto();
        reservationOrder.setName("黎明");
        reservationOrder.setIdcardNo("342225199109102078");
        reservationOrder.setPhone("18877885566");
        reservationOrder.setVerifyCode("4433");
        BaseStaffDTO baseStaffDTO = this.baseStaffService.queryByMobile("18875270068");
        reservationOrder.setStaffId(baseStaffDTO.getId());
        reservationOrder.setStaffName(baseStaffDTO.getUsername());
        reservationOrder.setStaffPhone(baseStaffDTO.getMobile());
        reservationOrder.setAppointStartTime(DateUtils.parseDate("2018-11-28 09:30", "yyyy-MM-dd HH:mm"));
        reservationOrder.setAppointEndTime(DateUtils.parseDate("2018-11-30 17:30", "yyyy-MM-dd HH:mm"));
        reservationOrder.setCompanions(1);
        reservationOrder.setRemark("开会");
        reservationOrder.setIsAddCarInfo(0);
        List<CarInfoEntity> cars = Lists.newArrayList();
        CarInfoEntity car = new CarInfoEntity();
        car.setCarNo("LEE001");
        car.setName("李小龙");
        car.setPhone("17601540345");
        car.setCreateTime(new Date());
        car.setUpdateTime(new Date());

        cars.add(car);
        reservationOrder.setCarInfoEntitys(cars);
        reservationOrder.setCompanionsEntitys(null);
        VisitorIdcardEntity idCardEntity = new VisitorIdcardEntity();
        idCardEntity.setPhysicalCardId("7788520");
        idCardEntity.setIdcardNo("342225199109102078");
        idCardEntity.setName("黎明");
        idCardEntity.setFacePhotoUrl("");
        idCardEntity.setIdcardPhotoUrl("");
        idCardEntity.setGender("男");
        idCardEntity.setAddress("香港尖沙嘴");
        idCardEntity.setIssuOrganization("香港皇家警察局");
        idCardEntity.setEffDate(DateUtils.parseDate("1965-08-08 09:30", "yyyy-MM-dd HH:mm"));
        idCardEntity.setExpDate(DateUtils.parseDate("2028-11-20 09:30", "yyyy-MM-dd HH:mm"));
        idCardEntity.setCreateTime(new Date());
        idCardEntity.setUpdateTime(new Date());

        // 模拟验证码，并设置到session中
        String verifyCode = VerifyCodeUtils.getValidationCode(4);
        HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(reservationOrder.getPhone(), verifyCode);

        reservationOrder.setVerifyCode(verifyCode);
        reservationOrder.setVisitorIdcardEntity(idCardEntity);
        reservationOrder.setCreateTime(new Date());
        reservationOrder.setUpdateTime(new Date());
        System.out.println("预约单请求JSON：" + JSONObject.toJSONString(reservationOrder));
        this.visitorReservationService.createReservationOrder(reservationOrder);
        System.out.println("创建预约单成功!");
    }

    /**
     * 验证预约单
     */
    @Test
    public void checkReservationOrder() {
        VisitorIdcardEntity visitorIdcard = new VisitorIdcardEntity();
        visitorIdcard.setPhysicalCardId(VerifyCodeUtils.getValidationCode(20));
        visitorIdcard.setIdcardNo("342225199109102078");
        visitorIdcard.setName("李森");
        visitorIdcard.setFacePhotoUrl("https://smartpark666.oss-cn-hangzhou.aliyuncs.com/20181117/img-9d160c267dcf660dbaad34c2c46c7f3a.jpg");
        visitorIdcard.setIdcardPhotoUrl("https://smartpark666.oss-cn-hangzhou.aliyuncs.com/20181117/img-9d160c267dcf660dbaad34c2c46c7f3a.jpg");
        visitorIdcard.setGender("男");
        visitorIdcard.setAddress("安徽合肥牛家庄牛人村第10086号");
        visitorIdcard.setIssuOrganization("安徽省合肥市天龙区公安分局");
        visitorIdcard.setEffDate(DateUtils.parseDate("2010-09-10 18:22"));
        visitorIdcard.setExpDate(DateUtils.parseDate("2030-09-10 18:33"));
        visitorIdcard.setCreateTime(new Date());
        visitorIdcard.setUpdateTime(new Date());
        System.out.println("访客身份信息JSON = " + JSONObject.toJSONString(visitorIdcard));
        this.visitorReservationService.checkIdCardAndGetAuth(visitorIdcard);
        System.out.println("访客身份校验成功！访客身份信息ID = " + visitorIdcard.getId());
    }

    @Test
    public void queryDoorControllers() {
        List<DoorEntity> list = this.entranceService.queryUserDoors("YzcmCZNvbXocrsz9dm8e");
        System.out.println("获取访客可授权的门禁列表JSON = " + JSONObject.toJSONString(list));
    }

}
