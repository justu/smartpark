package com.chris.smartpark.busi.entity;

import java.io.Serializable;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 工位门禁权限表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public class StationDoorEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//工位id
	private Long stationId;
	//门id
	private Long doorId;
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
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Long getStationId() {
		return stationId;
	}
	public void setDoorId(Long doorId) {
		this.doorId = doorId;
	}

	public Long getDoorId() {
		return doorId;
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
