package com.grgbanking.framework.domains.report.employee.monitoringEmployee.pojo;

import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;
import com.grgbanking.framework.domains.employeeDaily.pojo.EmployeeDailyPojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangweihua on 2018/1/26.
 */
public class MonitoringEmployeePojo implements Serializable {
    private String id;
    private String name;
    private String dept;
    private String ctfNo;
    private Date start_time;//开始时间
    private Date end_time;//结束时间
    private Date daily_time;//考勤时间
    private String daily_result;//考勤结果
    private Integer later_times;//迟到次数
    private Integer early_times;//早退次数
    private Integer normal_times;//正常次数
    private Integer attendance_days;//应出勤天数
    private Integer attendanced_days;//实际出勤天数
    private Double total_latertime;//迟到时长
    private Double total_earlytime;//早退时长
    private Integer no_check_in;//上班没签到
    private Integer no_check_out;//下班没签到
    private List<EmployeeDailyPojo> employeeDailyPojoList;
    private List<EmployeePojo> employeePojoList;

    public List<EmployeePojo> getEmployeePojoList() {
        return employeePojoList;
    }

    public void setEmployeePojoList(List<EmployeePojo> employeePojoList) {
        this.employeePojoList = employeePojoList;
    }

    public List<EmployeeDailyPojo> getEmployeeDailyPojoList() {
        return employeeDailyPojoList;
    }

    public void setEmployeeDailyPojoList(List<EmployeeDailyPojo> employeeDailyPojoList) {
        this.employeeDailyPojoList = employeeDailyPojoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public Date getDaily_time() {
        return daily_time;
    }

    public void setDaily_time(Date daily_time) {
        this.daily_time = daily_time;
    }

    public String getDaily_result() {
        return daily_result;
    }

    public void setDaily_result(String daily_result) {
        this.daily_result = daily_result;
    }

    public Integer getLater_times() {
        return later_times;
    }

    public void setLater_times(Integer later_times) {
        this.later_times = later_times;
    }

    public Integer getEarly_times() {
        return early_times;
    }

    public void setEarly_times(Integer early_times) {
        this.early_times = early_times;
    }

    public Integer getNormal_times() {
        return normal_times;
    }

    public void setNormal_times(Integer normal_times) {
        this.normal_times = normal_times;
    }

    public Integer getAttendance_days() {
        return attendance_days;
    }

    public void setAttendance_days(Integer attendance_days) {
        this.attendance_days = attendance_days;
    }

    public Integer getAttendanced_days() {
        return attendanced_days;
    }

    public void setAttendanced_days(Integer attendanced_days) {
        this.attendanced_days = attendanced_days;
    }

    public Double getTotal_latertime() {
        return total_latertime;
    }

    public void setTotal_latertime(Double total_latertime) {
        this.total_latertime = total_latertime;
    }

    public Double getTotal_earlytime() {
        return total_earlytime;
    }

    public void setTotal_earlytime(Double total_earlytime) {
        this.total_earlytime = total_earlytime;
    }

    public Integer getNo_check_in() {
        return no_check_in;
    }

    public void setNo_check_in(Integer no_check_in) {
        this.no_check_in = no_check_in;
    }

    public Integer getNo_check_out() {
        return no_check_out;
    }

    public void setNo_check_out(Integer no_check_out) {
        this.no_check_out = no_check_out;
    }
}
