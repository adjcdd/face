package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.param.MonitoringEmployeeListParam;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.pojo.MonitoringEmployeePojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.MonitoringEmployeeService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zhangweihua on 2018/1/29.
 */
public class MonitoringEmployeeEService {
    private Logger logger = LoggerFactory.getLogger(MonitoringEmployeeEService.class);
    private MonitoringEmployeeService monitoringEmployeeService = BeanFactoryConfig.getBean("monitoringEmployeeService");

    @EService("ES-MonitoringEmployee-List-T")
    @ManagerOperate("查询个人实时考勤报表")
    public RestResponse getDailyList(MonitoringEmployeeListParam monitoringEmployeeListParam)throws Exception{
        logger.info("开始执行查询个人实时考勤报表服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringEmployeeService.getMonitoringEmployeeList(monitoringEmployeeListParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询个人实时考勤报表发生异常");
        }
        return restResponse;
    }
  /*  @EService("ES-MonitoringEmployee-Export-T")
    @ManagerOperate("查询个人实时考勤报表")
    public RestResponse exportMonitoringEmployee(List<MonitoringEmployeePojo> monitoringEmployeePojoList, ServletOutputStream out)throws Exception{
        logger.info("开始执行到处个人实时考勤报表服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringEmployeeService.exportMonitoringEmployee(monitoringEmployeePojoList,out);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("到处个人实时考勤报表发生异常");
        }
        return restResponse;
    }*/
}
