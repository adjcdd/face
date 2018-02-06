package com.grgbanking.framework.domains.employeeDaily.pojo;

import com.grgbanking.framework.domains.employeeSign.pojo.EmployeeSignPojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangweihua on 2018/1/16.
 */
public class EmployeeDailyPojo implements Serializable {
    private Long id;
    private String name;
    private String uid;
    private Date check_in_time;
    private Date check_out_time;
    private Integer flag;
    private String dept;
    private String later;
    private String  early;
    private Double later_time;
    private Double early_time;

    public Double getLater_time() {
        return later_time;
    }

    public void setLater_time(Double later_time) {
        this.later_time = later_time;
    }

    public Double getEarly_time() {
        return early_time;
    }

    public void setEarly_time(Double early_time) {
        this.early_time = early_time;
    }

    private EmployeeSignPojo employeeSignPojo;

    public String getEarly() {
        return early;
    }

    public void setEarly(String early) {
        this.early = early;
    }

    public EmployeeSignPojo getEmployeeSignPojo() {
        return employeeSignPojo;
    }

    public void setEmployeeSignPojo(EmployeeSignPojo employeeSignPojo) {
        this.employeeSignPojo = employeeSignPojo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(Date check_in_time) {
        this.check_in_time = check_in_time;
    }

    public Date getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(Date check_out_time) {
        this.check_out_time = check_out_time;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLater() {
        return later;
    }

    public void setLater(String later) {
        this.later = later;
    }
}
