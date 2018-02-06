package com.grgbanking.framework.manager.mapper;


import com.grgbanking.framework.domains.employee.param.EmployeeAddParam;
import com.grgbanking.framework.domains.employee.param.EmployeeListParam;
import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wyf on 2017/8/1.
 */
@Service("EmployeeMapper")
public interface EmployeeMapper extends BaseMapper {

    List<EmployeePojo> getEmployeeList(EmployeeListParam employeeListParam);
    Long getEmployeeCount(EmployeeListParam employeeListParam);
    EmployeePojo getEmployeeInfo(EmployeePojo employeePojo);
    EmployeePojo getEmployeeInfoByName(EmployeePojo employeePojo);
    void addEmployee(EmployeePojo employeePojo);
    void deleteEmployee(EmployeePojo employeePojo);
    void updateEmployee(EmployeePojo employeePojo);
}
