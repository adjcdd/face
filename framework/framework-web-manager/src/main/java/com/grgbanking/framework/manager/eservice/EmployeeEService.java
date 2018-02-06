package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employee.param.EmployeeAddParam;
import com.grgbanking.framework.domains.employee.param.EmployeeListParam;
import com.grgbanking.framework.domains.employee.param.EmployeeUpdateParam;
import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;
import com.grgbanking.framework.domains.manager.user.param.UserListParam;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.EmployeeService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangweihua on 2017/12/18.
 */
public class EmployeeEService {
    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeService employeeService = BeanFactoryConfig.getBean("employeeService");

    @EService("ES-Employee-List-T")
    @ManagerOperate("查询员工列表")
    public RestResponse getEmployeeList(EmployeeListParam employeeListParam) throws Exception {
        logger.info("开始执行查询员工服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.employeeService.getEmployeeList(employeeListParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询员工列表发生异常");
        }
        return restResponse;
    }
    @EService("ES-Employee-Add-T")
    @ManagerOperate("新增员工")
    public RestResponse addEmployee(EmployeeAddParam employeeAddParam) throws Exception {
        logger.info("开始执行新增员工服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.employeeService.addEmployee(employeeAddParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("新增员工发生异常");
        }
        return restResponse;
    }
    @EService("ES-Employee-Info-T")
    @ManagerOperate("员工详情")
    public RestResponse getEmployeeInfo(EmployeePojo employeePojo) throws Exception {
        logger.info("开始执行查询员工详情，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.employeeService.getEmployeeInfo(employeePojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询员工详情发生异常");
        }
        return restResponse;
    }
    @EService("ES-Employee-Delete-T")
    @ManagerOperate("删除员工")
    public RestResponse deleteEmployee(EmployeePojo employeePojo) throws  Exception{
        logger.info("开始执行删除员工，请求标示为: "+RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse= new RestResponse();
        try{
            restResponse=this.employeeService.deleteEmployee(employeePojo);
        }catch(Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("删除员工发生异常");
        }
        return restResponse;
    }
    @EService("ES-Employee-Update-T")
    @ManagerOperate("更新员工")
    public RestResponse updateEmployee(EmployeeUpdateParam employeeUpdateParam) throws Exception{
        logger.info("开始执行更新员工，请求标示为: "+RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse= new RestResponse();
        try{
            restResponse=this.employeeService.updateEmployee(employeeUpdateParam);
        }catch(Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("更新员工发生异常");
        }
        return restResponse;
    }
}
