package com.grgbanking.framework.domains.employee.param;

import com.grgbanking.framework.domains.common.param.BaseParam;

import java.io.Serializable;

/**
 * Created by zhangweihua on 2017/12/18.
 */
public class EmployeeListParam extends BaseParam implements Serializable{
    private String name;
    private String ctfNo;
    private String uid;
    private String dept;
    private Integer pageNo;
    private Integer pageSize;
    private Integer currentCount;


    public String getCtfNo() {
        return ctfNo;
    }

    public void setCtfNo(String ctfNo) {
        this.ctfNo = ctfNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }
}
