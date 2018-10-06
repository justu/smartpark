package com.chris.smartpark.busi.entity;

import java.io.Serializable;
import java.util.Date;

import com.chris.base.common.model.SysUpdateInfo;


/**
 * 访客表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 07.18
 */
public class VisitorInfoEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//访客姓名
	private String name;
	//访客性别
	private String gender;
	//访客民族
	private String nation;
	//访客生日
	private Date birthday;
	//访客身份证号码
	private String idcardNo;
	//访客照片
	private String photoUrl;
	//备注信息
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
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getIdcardNo() {
		return idcardNo;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
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
