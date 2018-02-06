package com.grgbanking.framework.manager.resource;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleListParam;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.param.MonitoringEmployeeListParam;
import com.grgbanking.framework.manager.service.EmployeeDailyRuleService;
import com.grgbanking.framework.manager.service.MonitoringEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangweihua on 2018/2/2.
 */

@Controller
public class ExportController {

    @Autowired
    private MonitoringEmployeeService monitoringEmployeeService;

    @RequestMapping(value="/exportMonitoringEmployee",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse exportMonitoringEmployee(MonitoringEmployeeListParam monitoringEmployeeListParam, HttpServletRequest request, HttpServletResponse response) throws Exception{
        RestResponse restResponse=new RestResponse();
        try{
            restResponse=this.monitoringEmployeeService.exportMonitoringEmployee(monitoringEmployeeListParam,request,response);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("导出个人实时考勤发生异常");
        }
        return restResponse;
    }
    @Autowired
    private EmployeeDailyRuleService employeeDailyRuleService;
    @RequestMapping(value="/exportEmployeeDailyRule",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse exportEmployeeDaileRule(EmployeeDailyRuleListParam employeeDailyRuleListParam, HttpServletRequest request, HttpServletResponse response) throws Exception{
        RestResponse restResponse=new RestResponse();
        try{
            restResponse=this.employeeDailyRuleService.exportEmployeeDailyRule(employeeDailyRuleListParam,request,response);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("导出考勤规则报表发生异常");
        }
        return restResponse;
    }

}
