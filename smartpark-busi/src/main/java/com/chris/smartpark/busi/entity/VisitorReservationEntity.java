package com.chris.smartpark.busi.entity;

import java.io.Serializable;
import java.util.Date;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 访客预约登记单
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public class VisitorReservationEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//订单id
	private Integer id;
	//访客手机号
	private String phone;
	//访客照片
	private String photoUrl;
	//访客id
	private Integer visitorId;
	//受访者id
	private Integer intervieweeId;
	//订单状态
	private String status;
	//预约开始时间
	private Date appointStartTime;
	//预约结束时间
	private Date appointEndTime;
	//实际开始时间
	private Date actStartTime;
	//实际结束时间
	private Date actEndTime2;
	//预约单类型
	private String type;
	//备注(来访目的)
	private String remark;
	//同行人数
	private Integer companions;
	//扩展字段1
	private String ext1;
	//扩展字段2
	private String ext2;
	//扩展字段3
	private String ext3;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	public Integer getVisitorId() {
		return visitorId;
	}
	public void setIntervieweeId(Integer intervieweeId) {
		this.intervieweeId = intervieweeId;
	}

	public Integer getIntervieweeId() {
		return intervieweeId;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	public void setAppointStartTime(Date appointStartTime) {
		this.appointStartTime = appointStartTime;
	}

	public Date getAppointStartTime() {
		return appointStartTime;
	}
	public void setAppointEndTime(Date appointEndTime) {
		this.appointEndTime = appointEndTime;
	}

	public Date getAppointEndTime() {
		return appointEndTime;
	}
	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}

	public Date getActStartTime() {
		return actStartTime;
	}
	public void setActEndTime2(Date actEndTime2) {
		this.actEndTime2 = actEndTime2;
	}

	public Date getActEndTime2() {
		return actEndTime2;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
	public void setCompanions(Integer companions) {
		this.companions = companions;
	}

	public Integer getCompanions() {
		return companions;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt1() {
		return ext1;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt2() {
		return ext2;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt3() {
		return ext3;
	}
}
