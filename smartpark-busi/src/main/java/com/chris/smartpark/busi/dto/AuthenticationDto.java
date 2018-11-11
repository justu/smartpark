package com.chris.smartpark.busi.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by lisen on 2018/11/7.
 */
public class AuthenticationDto {
    //姓名

    private String name;
    //物理卡id

    private String physicalCardId;
    //身份证号

    private String idcardNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalCardId() {
        return physicalCardId;
    }

    public void setPhysicalCardId(String physicalCardId) {
        this.physicalCardId = physicalCardId;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

}
