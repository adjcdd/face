package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.report.employee.monitoringEmployee.param.MonitoringEmployeeListParam;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.pojo.MonitoringEmployeePojo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangweihua on 2018/1/26.
 */
@Service("monitoringEmployeeMapper")
public interface MonitoringEmployeeMapper extends BaseMapper {
    void addMonitoringEmployee(MonitoringEmployeePojo monitoringEmployeePojo);
    List<MonitoringEmployeePojo> getList(MonitoringEmployeeListParam monitoringEmployeeListParam);
    List<MonitoringEmployeePojo> exportList(MonitoringEmployeeListParam monitoringEmployeeListParam);
    List<MonitoringEmployeePojo> getCheckTime(MonitoringEmployeeListParam monitoringEmployeeListParam);
    Long getMonitoringEmployeeCount(MonitoringEmployeeListParam monitoringEmployeeListParam);
}
