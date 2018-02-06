package com.grgbanking.framework.domains.common.param;

import java.io.Serializable;

/**
 * Created by wyf on 2017/6/28.
 * 参数实体公共父类，记录业务流水号
 */
public class BaseParam implements Serializable{

    /**
     * 业务流水号
     */
    private String serialNo;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端IP地址
     */
    private String ipAddr;

    /**
     * 调用算法的厂商类型
     */
    private String manType="authenmetric";

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getManType() {
        return manType;
    }

    public void setManType(String manType) {
        this.manType = manType;
    }
}
