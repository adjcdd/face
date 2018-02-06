package com.grgbanking.framework.domains.employeeDailyRule.param;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangweihua on 2018/1/18.
 */
public class EmployeeDailyRuleInfoParam implements Serializable {
    private Long id;
    private String title;
    private String check_in_time;
    private String check_out_time;
    private String dept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
