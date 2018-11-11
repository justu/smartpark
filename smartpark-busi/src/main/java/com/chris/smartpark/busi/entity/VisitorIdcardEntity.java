package com.chris.smartpark.busi.entity;

import java.io.Serializable;
import java.util.Date;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 访客身份信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Nov 11.18
 */
public class VisitorIdcardEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//访客id
	private Long visitorId;
	//物理卡id
	private String physicalCardId;
	//身份证号
	private String idcardNo;
	//姓名
	private String name;
	//人脸识别照片
	private String facePhotoUrl;
	//身份证照片
	private String idcardPhotoUrl;
	//性别  1 男  2 女
	private Integer sex;
	//住址
	private String address;
	//签发机关
	private String issuOrganization;
	//生效日期
	private Date effDate;
	//失效日期
	private Date expDate;
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
	public void setVisitorId(Long visitorId) {
		this.visitorId = visitorId;
	}

	public Long getVisitorId() {
		return visitorId;
	}
	public void setPhysicalCardId(String physicalCardId) {
		this.physicalCardId = physicalCardId;
	}

	public String getPhysicalCardId() {
		return physicalCardId;
	}
	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getIdcardNo() {
		return idcardNo;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setFacePhotoUrl(String facePhotoUrl) {
		this.facePhotoUrl = facePhotoUrl;
	}

	public String getFacePhotoUrl() {
		return facePhotoUrl;
	}
	public void setIdcardPhotoUrl(String idcardPhotoUrl) {
		this.idcardPhotoUrl = idcardPhotoUrl;
	}

	public String getIdcardPhotoUrl() {
		return idcardPhotoUrl;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSex() {
		return sex;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
	public void setIssuOrganization(String issuOrganization) {
		this.issuOrganization = issuOrganization;
	}

	public String getIssuOrganization() {
		return issuOrganization;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Date getEffDate() {
		return effDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Date getExpDate() {
		return expDate;
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
