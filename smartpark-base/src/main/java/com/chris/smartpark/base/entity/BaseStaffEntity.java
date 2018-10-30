package com.chris.smartpark.base.entity;

import java.io.Serializable;
import java.util.Date;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 园区员工表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public class BaseStaffEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//员工ID
	private Integer id;
	//员工姓名
	private String name;
	//员工工号
	private String workNo;
	//部门ID
	private Integer deptId;
	//公司IDv
	private Integer companyId;
	//园区ID
	private Integer parkId;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
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
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getDeptId() {
		return deptId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}

	public Integer getParkId() {
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
