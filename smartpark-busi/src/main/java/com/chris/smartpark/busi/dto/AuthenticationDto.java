package com.chris.smartpark.busi.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by lisen on 2018/11/7.
 */
public class AuthenticationDto {
    //姓名
    @NotNull(message = "姓名必填", groups = {AuthenticationDto.ValidateIdentity.class})
    private String name;
    //物理卡id
    @NotNull(message = "物理卡id必填", groups = {AuthenticationDto.ValidateIdentity.class})
    private String physicalCardId;
    //身份证号
    @NotNull(message = "身份证号必填", groups = {AuthenticationDto.ValidateIdentity.class})
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

    /* ********************** 参数校验类 *******************************/
    public interface ValidateIdentity{

    }
}
