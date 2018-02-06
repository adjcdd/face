package com.grgbanking.framework.domains.employee.param;

import com.grgbanking.framework.domains.common.param.BaseParam;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangweihua on 2017/12/23.
 */
public class EmployeeUpdateParam extends BaseParam implements Serializable {
    private String uid;
    private String name;
    private Integer sex;
    private String telphone;
    private String ctfNo;
    private String email;
    private String image;
    private String dept;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getCtfNo() {
        return ctfNo;
    }

    public void setCtfNo(String ctfNo) {
        this.ctfNo = ctfNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
