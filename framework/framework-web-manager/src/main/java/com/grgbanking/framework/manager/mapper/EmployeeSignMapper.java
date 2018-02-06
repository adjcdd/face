package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.employeeSign.pojo.EmployeeSignPojo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangweihua on 2018/1/8.
 */
@Service("employeeSignMapper")
public interface EmployeeSignMapper extends BaseMapper{
    List<EmployeeSignPojo> getSignList();
    List<EmployeeSignPojo> getSignFailList();
    List<EmployeeSignPojo> getSignClockLastime();
    void addEmployeeSign(EmployeeSignPojo employeeSignPojo);
    void updateEmployeeSign(EmployeeSignPojo employeeSignPojo);

}
