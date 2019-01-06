package com.chris.smartpark;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONObject;
import com.chris.BusiApplication;
import com.chris.base.common.utils.DateUtils;
import com.chris.base.common.utils.HttpContextUtils;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.VerifyCodeUtils;
import com.chris.base.modules.sys.service.SysConfigService;
import com.chris.smartpark.base.dto.BaseStaffDTO;
import com.chris.smartpark.base.service.BaseStaffService;
import com.chris.smartpark.busi.common.BeanUtil;
import com.chris.smartpark.busi.common.JDBCParam;
import com.chris.smartpark.busi.common.JDBCUtils4SQLServer;
import com.chris.smartpark.busi.common.VisitorConstants;
import com.chris.smartpark.busi.dto.ReservationOrderApproveDTO;
import com.chris.smartpark.busi.dto.ReservationOrderDTO;
import com.chris.smartpark.busi.entity.*;
import com.chris.smartpark.busi.service.EntranceService;
import com.chris.smartpark.busi.service.VisitorInfoHisService;
import com.chris.smartpark.busi.service.VisitorInfoService;
import com.chris.smartpark.busi.service.VisitorReservationService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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

	@Autowired
	private VisitorInfoHisService visitorInfoHisService;

	@Autowired
	private SysConfigService sysConfigService;

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
        ReservationOrderDTO reservationOrder = this.visitorReservationService.queryReservationOrderDetail(3L);
        System.out.println("预约单JSON = " + JSONObject.toJSONString(reservationOrder));
    }

    /**
     * 创建预约单
     */
	@Test
	public void createReservationOrder() {
        ReservationOrderDTO reservationOrder = new ReservationOrderDTO();
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

    /**
     * 预约审核
     */
    @Test
    public void reservationApprove() {
        ReservationOrderApproveDTO authorizeDTO = new ReservationOrderApproveDTO();
        authorizeDTO.setReservationId(5L);
        authorizeDTO.setDoorList(ImmutableList.of(4L, 6L));
        authorizeDTO.setApproveReslut(VisitorConstants.ApproveResult.OK);
//        authorizeDTO.setRejectReaon("");
        authorizeDTO.setOpenId("YzcmCZNvbXocrsz9dm8e"); // 授权人openId
        authorizeDTO.setActStartTime(DateUtils.parseDate("2018-11-20 09:00"));
        authorizeDTO.setActEndTime(DateUtils.parseDate("2018-11-20 18:00"));

        System.out.println("预约审核请求JSON = " + JSONObject.toJSONString(authorizeDTO));
        this.visitorReservationService.approve(authorizeDTO);
        System.out.println("预约审核结束.....");
    }

    /**
     * 根据openid查询员工对应的预约单列表
     */
    @Test
    public void queryReservationOrdersByOpenId() {
        PageUtils page = this.visitorReservationService.queryReservationOrdersByOpenId(ImmutableMap.of(VisitorConstants.Keys.OPEN_ID, "obETm5c3-tUxjFf3Rgq5qRbZPRfk",
                VisitorConstants.Keys.PAGE, "1", VisitorConstants.Keys.LIMIT, "10"));
        System.out.println("查询员工对应的预约单JSON = " + JSONObject.toJSONString(page));
    }

    @Test
    public void queryReservationOrderDetail() {
        ReservationOrderDTO detail = this.visitorReservationService.queryReservationOrderDetail(6L);
        System.out.println("查询预约单详情JSON = " + JSONObject.toJSONString(detail));
    }

    /**
     * 根据身份证号查询访客
     */
    @Test
    public void queryVisitorHisByIdcardNo() {
        VisitorInfoHisEntity visitor = this.visitorInfoHisService.queryByIdcardNo("342225199109102078");
        System.out.println("根据身份证号查询访客实例信息 = " + JSONObject.toJSONString(visitor));
    }

    /**
     * 同步门禁信息
     */
    @Test
    public void syncDoorCtrl() throws Exception{
        String json = this.sysConfigService.getValue(VisitorConstants.Keys.SQLSERVER_CONFIG);
        JDBCParam jdbcParam = JSONObject.parseObject(json, JDBCParam.class);
        jdbcParam.setSql("INSERT INTO NDr2_AuthorSet1 ([CardID], [DoorID], [PassWord], [DueDate], [AuthorType], [AuthorStatus], [UserTimeGrp], [DownLoaded], [FirstDownLoaded], [PreventCard], [StartTime]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        List<DoorAuthEntity> doorAuthList = this.buildDoorAuthList();
        JDBCUtils4SQLServer.saveDoorAuthoRecords(jdbcParam, doorAuthList);
        System.out.println("同步门禁信息成功！");
    }

    private List<DoorAuthEntity> buildDoorAuthList() {
        //一个门对应两条数据
        DoorAuthEntity door1 = new DoorAuthEntity();
        DoorAuthEntity door2 = new DoorAuthEntity();
        door1.setCardID(8893868);
        door1.setDoorID(77);
        door1.setPassWord("0000");
        door1.setDueDate(DateUtils.parseDate("2099-12-31"));
        door1.setAuthorType(0);
        door1.setAuthorStatus(0);
        door1.setUserTimeGrp(0);
        door1.setDownLoaded(0);
        door1.setFirstDownLoaded(0);
        door1.setPreventCard(0);
        door1.setStartTime(DateUtils.parseDate("2018-12-28 09:15:00", "yyyy-MM-dd HH:mm:ss"));

        BeanUtil.copyProperties(door1, door2);
        door2.setAuthorStatus(2);
        door2.setStartTime(DateUtils.parseDate("2018-12-28 19:35:00", "yyyy-MM-dd HH:mm:ss"));
        return Lists.newArrayList(door1, door2);
    }

}
