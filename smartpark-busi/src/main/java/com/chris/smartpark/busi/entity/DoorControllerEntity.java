package com.chris.smartpark.busi.entity;

import java.io.Serializable;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 门控制信息
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public class DoorControllerEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//门id
	private Long doorId;
	//门对应的控制器id
	private Long controllerId;
	//控制器名称
	private String controllerName;
	//控制器编号
	private String controllerNo;
	//控制器IP
	private String controllerIp;
	//控制器端口
	private String controllerPort;
	//状态 0 废弃 1正常
	private String status;
	//备注
	private String remark;
	//扩展字段1
	private String ext1;
	//扩展字段2
	private String ext2;
	//扩展字段3
	private String ext3;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setDoorId(Long doorId) {
		this.doorId = doorId;
	}

	public Long getDoorId() {
		return doorId;
	}
	public void setControllerId(Long controllerId) {
		this.controllerId = controllerId;
	}

	public Long getControllerId() {
		return controllerId;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getControllerName() {
		return controllerName;
	}
	public void setControllerNo(String controllerNo) {
		this.controllerNo = controllerNo;
	}

	public String getControllerNo() {
		return controllerNo;
	}
	public void setControllerIp(String controllerIp) {
		this.controllerIp = controllerIp;
	}

	public String getControllerIp() {
		return controllerIp;
	}
	public void setControllerPort(String controllerPort) {
		this.controllerPort = controllerPort;
	}

	public String getControllerPort() {
		return controllerPort;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
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
