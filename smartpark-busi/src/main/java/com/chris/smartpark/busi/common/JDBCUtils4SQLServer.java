package com.chris.smartpark.busi.common;

import com.chris.smartpark.busi.entity.DoorAuthEntity;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
public final class JDBCUtils4SQLServer {

    private static Connection conn = null;

    private static PreparedStatement ps = null;

    private static ResultSet rs = null;


    private static Connection getConnection(JDBCParam jdbcParam) {
        try {
            Class.forName(jdbcParam.getDriver());
            conn = DriverManager.getConnection(jdbcParam.getUrl(), jdbcParam.getUser(), jdbcParam.getPassword());// 获得连接对象
            log.info("成功加载 SQL Server 驱动程序");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("找不到 SQL Server 驱动程序, 原因：{}", e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("创建 SQL Server 连接异常, 原因：{}", e.getMessage());
        }
        return conn;
    }

    public static ResultSet select(JDBCParam jdbcParam) throws Exception {
        try {
            conn = getConnection(jdbcParam);
            ps = conn.prepareStatement(jdbcParam.getSql());
            rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询 SQL 异常!原因：{}", e.getMessage());
            throw new Exception("查询 SQL 异常!");
        }
    }

    public static void saveDoorAuthoRecords(JDBCParam jdbcParam, List<DoorAuthEntity> doorAuthList) throws Exception {
        try {
            conn = getConnection(jdbcParam);
            ps = conn.prepareStatement(jdbcParam.getSql());
            int len = doorAuthList.size();
            for (int i = 0; i < len; i++) {
                ps.setInt(1, doorAuthList.get(i).getCardID());
                ps.setInt(2, doorAuthList.get(i).getDoorID());
                ps.setString(3, doorAuthList.get(i).getPassWord());
                ps.setTimestamp(4, new Timestamp(doorAuthList.get(i).getDueDate().getTime()));
                ps.setInt(5, doorAuthList.get(i).getAuthorType());
                ps.setInt(6, doorAuthList.get(i).getAuthorStatus());
                ps.setInt(7, doorAuthList.get(i).getUserTimeGrp());
                ps.setInt(8, doorAuthList.get(i).getDownLoaded());
                ps.setInt(9, doorAuthList.get(i).getFirstDownLoaded());
                ps.setInt(10, doorAuthList.get(i).getPreventCard());
                ps.setTimestamp(11, new Timestamp(doorAuthList.get(i).getStartTime().getTime()));
                //if(ps.executeUpdate() != 1) r = false;    优化后，不用传统的插入方法了。
                //优化插入第二步       插入代码打包，等一定量后再一起插入。
                ps.addBatch();
                //if(ps.executeUpdate() != 1)result = false;
                //每200次提交一次
                if ((i != 0 && i % 200 == 0) || i == len - 1) {//可以设置不同的大小；如50，100，200，500，1000等等
                    ps.executeBatch();
                    //优化插入第三步       提交，批量插入数据库中。
                    conn.commit();
                    ps.clearBatch();        //提交后，Batch清空。
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("更新 SQL 操作异常，原因：{}", e.getMessage());
            throw new SQLException("update data Exception: " + e.getMessage());
        } finally {
            close();
        }
    }

    private static void close() throws Exception {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ps close exception: " + e.getMessage());
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("conn close exception: " + e.getMessage());
        }
    }
}