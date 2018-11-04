package com.chris.smartpark.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 园区员工表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 04.18
 */
public class BaseStaffEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//员工ID
	private Long id;
	//员工姓名
	private String name;
	//员工工号
	private String workNo;
	//部门ID
	private Long deptId;
	//公司IDv
	private Long companyId;
	//园区ID
	private Long parkId;
	//身份证
	private String idCard;
	//职务
	private String position;
	//性别
	private String gender;
	//出生日期
	private Date bornDate;
	//状态
	private String status;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getWorkNo() {
		return workNo;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public Long getParkId() {
		return parkId;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCard() {
		return idCard;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public String getPosition() {
		return position;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public Date getBornDate() {
		return bornDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
