package com.grgbanking.framework.domains.employee.pojo;

import com.grgbanking.framework.domains.common.param.BaseParam;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangweihua on 2017/12/18.
 */
public class EmployeePojo extends BaseParam implements Serializable{
    private Long id;
    private String uid;
    private String name;
    private Integer sex;
    private Date createTime;
    private String telphone;
    private String ctfNo;
    private String email;
    private byte[] image;
    private String dept;
    private String imageBase64;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }


}
