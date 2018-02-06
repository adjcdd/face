package com.grgbanking.framework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangweihua on 2018/1/26.
 */



@Controller
public class MonitoringEmployeeReportController {
    @RequestMapping(value = "/toMonitoringEmployeeReport", method = RequestMethod.GET)
    public String toMonitoringEmployeeReport(Model model) throws Exception {
        return "report/employeeReport/monitoringEmployeeReport/ui/monitoringEmployeeReport";
    }
    @RequestMapping(value = "/toHistoryEmployeeReport", method = RequestMethod.GET)
    public String toHistoryEmployeeReport(Model model) throws Exception {
        return "report/employeeReport/historyEmployeeReport/ui/historyEmployeeReport";
    }


}
