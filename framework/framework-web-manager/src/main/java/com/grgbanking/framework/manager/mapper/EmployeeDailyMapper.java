package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.employeeDaily.pojo.EmployeeDailyPojo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangweihua on 2018/1/16.
 */
@Service("employeeDailyMapper")
public interface EmployeeDailyMapper extends BaseMapper{
    void addEmployeeDaily(EmployeeDailyPojo employeeDailyPojo);
    void editEmployeeDaily(EmployeeDailyPojo employeeDailyPojo);
    EmployeeDailyPojo getEmployeeDaily(EmployeeDailyPojo employeeDailyPojo);
    List<EmployeeDailyPojo> getEmployeeDailyList(EmployeeDailyPojo employeeDailyPojo);
    List<EmployeeDailyPojo> getCurrentDailyList();
    List<EmployeeDailyPojo> getEmployeeDailyCurrentDayList();
}
