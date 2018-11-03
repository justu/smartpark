package com.chris.smartpark.base.entity;

import java.io.Serializable;
import com.chris.base.common.model.SysUpdateInfo;


/**
 * 园区公司信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Oct 16.18
 */
public class BaseCompanyEntity  extends SysUpdateInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//公司ID
	private Integer id;
	//公司名称
	private String companyName;
	//园区ID
	private Integer parkId;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}

	public Integer getParkId() {
		return parkId;
	}
}
