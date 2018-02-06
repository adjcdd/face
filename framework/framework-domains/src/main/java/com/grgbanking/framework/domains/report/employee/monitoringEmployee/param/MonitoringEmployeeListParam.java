package com.grgbanking.framework.domains.report.employee.monitoringEmployee.param;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangweihua on 2018/1/26.
 */
public class MonitoringEmployeeListParam implements Serializable{

    private String name;
    private String ctfNo;
    private Date start_time;
    private Date end_time;
    private String daily_result;
    private Integer pageNo;
    private Integer pageSize;
    private Integer currentCount;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCtfNo() {
        return ctfNo;
    }

    public void setCtfNo(String ctfNo) {
        this.ctfNo = ctfNo;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getDaily_result() {
        return daily_result;
    }

    public void setDaily_result(String daily_result) {
        this.daily_result = daily_result;
    }
}
