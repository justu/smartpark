package com.chris.smartpark.busi.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 批量车辆信息导入DTO
 */
public class BatchCarInfoImportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "批次号")
    private String batchNo;

    @Excel(name = "车牌号")
    private String carNo;

    @Excel(name = "驾驶员姓名")
    private String name;

    @Excel(name = "驾驶员手机号")
    private String phone;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
