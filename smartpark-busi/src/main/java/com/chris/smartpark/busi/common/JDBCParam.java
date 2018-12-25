package com.chris.smartpark.busi.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class JDBCParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String driver;

    private String url;

    private String user;

    private String password;

    private String sql;

}
