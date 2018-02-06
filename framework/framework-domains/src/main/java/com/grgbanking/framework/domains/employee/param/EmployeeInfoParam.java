package com.grgbanking.framework.domains.employee.param;

import com.grgbanking.framework.domains.common.param.BaseParam;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangweihua on 2017/12/22.
 */
public class EmployeeInfoParam extends BaseParam implements Serializable {
    private Long id;
    private String employee_dept;
    private Integer sex;
    private String name;
    private Date createTime;
    private String telphone;
    private String ctfNo;
    private String email;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployee_dept() {
        return employee_dept;
    }

    public void setEmployee_dept(String employee_dept) {
        this.employee_dept = employee_dept;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
