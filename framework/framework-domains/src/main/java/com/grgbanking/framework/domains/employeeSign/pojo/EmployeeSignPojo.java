package com.grgbanking.framework.domains.employeeSign.pojo;

import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by zhangweihua on 2018/1/8.
 */
public class EmployeeSignPojo implements Serializable {
    private Long id;
    private String uid;
    private Date clock_in_time;
    private String device_no;
    private Integer sign;
    private byte[] image;
    private String imageBase64;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getClock_in_time() {
        return clock_in_time;
    }

    public void setClock_in_time(Date clock_in_time) {
        this.clock_in_time = clock_in_time;
    }

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @Override
    public String toString() {
        return "EmployeeSignPojo{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", clock_in_time=" + clock_in_time +
                ", device_no=" + device_no +
                ", sign=" + sign +
                ", imageBase64='" + imageBase64 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
