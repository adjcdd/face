package com.grgbanking.framework.domains.employeeSign.param;

import java.io.Serializable;

/**
 * Created by zhangweihua on 2018/1/4.
 */
public class EmployeeSignIdentifyParam implements Serializable {
    private String image;
    private String device_no;
    private String manType="authenmetric";//="authenmetric";algorithm  authenmetric

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public String getManType() {
        return manType;
    }

    public void setManType(String manType) {
        this.manType = manType;
    }
}
