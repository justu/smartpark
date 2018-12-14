package com.chris.smartpark.busi.common;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.CacheDataUtils;
import com.chris.base.common.utils.SpringContextUtils;
import com.chris.smartpark.busi.entity.DoorControllerEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
public final class DoorControllerProcessor {

    /**
     * 远程开门
     * @param doorController
     */
    public static void remoteOpenDoor(DoorControllerEntity doorController) {
        String deviceIP = doorController.getControllerIp();
        String deviceMacAddr = doorController.getMacAddr();
        int readerNo = doorController.getReaderNo();
        DatagramSocket client = null;
        DatagramPacket sendPacket;
        DatagramPacket receivePacket;
        try {
            client = new DatagramSocket();
            client.setSoTimeout(3000);
            byte[] sendBuf = buildSendBuff(readerNo, deviceMacAddr);

            InetAddress addr = InetAddress.getByName(deviceIP);

            sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, getSendPort());
            client.send(sendPacket);

            byte[] data2 = new byte[1024];
            receivePacket = new DatagramPacket(data2, data2.length, addr, getReceivePort());
            log.error("设备[{}] 发送指令= {}", deviceMacAddr, bytesToHexString(sendBuf));
            client.receive(receivePacket);
            String receiveData = bytesToHexString(receivePacket.getData());
            log.error("设备[{}] 接收指令= {}", deviceMacAddr, receiveData);
            parseReceiveData(receiveData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("远程开门异常！原因：{}", e.getMessage());
            throw new CommonException("远程开门异常！原因：" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(client);
        }
    }

    private static void parseReceiveData(String receiveData) {
        // TODO 待处理
    }

    private static byte[] buildSendBuff(int readerNo, String deviceMacAddr) {
        byte[] sendBuf = new byte[12];
        byte[] bytes = new byte[3];
        bytes[0] = (byte) Integer.parseInt(deviceMacAddr.substring(0, 2), 16);
        bytes[1] = (byte) Integer.parseInt(deviceMacAddr.substring(2, 4), 16);
        bytes[2] = (byte) Integer.parseInt(deviceMacAddr.substring(4, 6), 16);
        sendBuf[0] = (byte) 0x55;
        sendBuf[1] = (byte) 0x04;
        sendBuf[2] = bytes[0];
        sendBuf[3] = bytes[1];
        sendBuf[4] = bytes[2];
        sendBuf[5] = (byte) (0x2D);
        sendBuf[6] = (byte) 0x00;
        sendBuf[7] = (byte) 0x01;
        sendBuf[8] = (byte) (readerNo + 1);
        sendBuf[9] = (byte) 0x00;
        int path = 0;
        for (int i = 0; i < 10; i++) {
            path = path + (sendBuf[i] & 0xff);
        }
        sendBuf[10] = (byte) ((path >> 8) & 0xff);
        sendBuf[11] = (byte) (path & 0xff);
        return sendBuf;
    }


    private static int getSendPort() {
        return Integer.valueOf(getCacheDataUtils().getConfigValueByKey(VisitorConstants.Keys.DOOR_CTRL_SEND_PORT));
    }


    private static int getReceivePort() {
        return Integer.valueOf(getCacheDataUtils().getConfigValueByKey(VisitorConstants.Keys.DOOR_CTRL_RECEIVE_PORT));
    }

    /**
     * 数组转换成十六进制字符串
     * @return HexString
     */
    private static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static CacheDataUtils getCacheDataUtils() {
        return (CacheDataUtils) SpringContextUtils.getBean("cacheDataUtils");
    }
}
