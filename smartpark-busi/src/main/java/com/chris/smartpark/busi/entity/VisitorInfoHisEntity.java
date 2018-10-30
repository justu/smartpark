package com.chris.smartpark.busi.entity;

import java.io.Serializable;
import java.util.Date;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 访客信息历史表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 30.18
 */
public class VisitorInfoHisEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//访客id
	private Integer visitorId;
	//民族
	private String nation;
	//生日
	private Date birthday;
	//姓名
	private String name;
	//性别
	private String gender;
	//身份证号
	private String idcardNo;
	//手机号
	private String phone;
	//访客类型
	private Integer type;
	//访客照片
	private String photoUrl;
	//车牌号
	private String carNo;
	//单位名称
	private String company;
	//第三方平台关联id
	private String uuid;
	//备注
	private String remark;
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
	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	public Integer getVisitorId() {
		return visitorId;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNation() {
		return nation;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getIdcardNo() {
		return idcardNo;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarNo() {
		return carNo;
	}
	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return company;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
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
