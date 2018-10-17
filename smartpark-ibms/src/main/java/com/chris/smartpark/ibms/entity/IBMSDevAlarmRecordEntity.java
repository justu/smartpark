package com.chris.smartpark.ibms.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备报警记录表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
public class IBMSDevAlarmRecordEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//记录ID
	private Integer id;
	//设备ID
	private Integer deviceId;
	//子系统ID
	private Integer subsystemId;
	//是否告警
	private Integer alarm;
	//告警级别
	private Integer alarmLevel;
	//告警原因
	private String reason;
	//创建时间
	private Date createTime;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}
	public void setSubsystemId(Integer subsystemId) {
		this.subsystemId = subsystemId;
	}

	public Integer getSubsystemId() {
		return subsystemId;
	}
	public void setAlarm(Integer alarm) {
		this.alarm = alarm;
	}

	public Integer getAlarm() {
		return alarm;
	}
	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}
}
