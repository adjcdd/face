package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleAddParam;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleListParam;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleUpdateParam;
import com.grgbanking.framework.domains.employeeDailyRule.pojo.EmployeeDailyRulePojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.EmployeeDailyRuleService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by zhangweihua on 2018/1/18.
 */
public class EmployeeDailyRuleEService {
    private Logger logger = LoggerFactory.getLogger(EmployeeDailyRuleService.class);
    private EmployeeDailyRuleService employeeDailyRuleService = BeanFactoryConfig.getBean("employeeDailyRuleService");

    @EService("ES-EmployeeDailyRule-List-T")
    @ManagerOperate("查询规则列表")
    public RestResponse getEmployeeDailyRuleList(EmployeeDailyRuleListParam employeeDailyRuleListParam) throws Exception {
        logger.info("开始执行查询规则服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.employeeDailyRuleService.getEmployeeDailyRuleList(employeeDailyRuleListParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询规则列表发生异常");
        }
        return restResponse;
    }
    @EService("ES-EmployeeDailyRule-Add-T")
    @ManagerOperate("新增规则")
    public RestResponse addEmployeeDailyRule(EmployeeDailyRuleAddParam EmployeeDailyRuleAddParam) throws Exception {
        logger.info("开始执行新增规则服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.employeeDailyRuleService.addEmployeeDailyRule(EmployeeDailyRuleAddParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("新增规则发生异常");
        }
        return restResponse;
    }
    @EService("ES-EmployeeDailyRule-Info-T")
    @ManagerOperate("规则详情")
    public RestResponse getEmployeeDailyRuleInfo(EmployeeDailyRulePojo EmployeeDailyRulePojo) throws Exception {
        logger.info("开始执行查询规则详情，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.employeeDailyRuleService.getEmployeeDailyRuleInfo(EmployeeDailyRulePojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询规则详情发生异常");
        }
        return restResponse;
    }
    @EService("ES-EmployeeDailyRule-Delete-T")
    @ManagerOperate("删除规则")
    public RestResponse deleteEmployeeDailyRule(EmployeeDailyRulePojo EmployeeDailyRulePojo) throws  Exception{
        logger.info("开始执行删除规则，请求标示为: "+RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse= new RestResponse();
        try{
            restResponse=this.employeeDailyRuleService.deleteEmployeeDailyRule(EmployeeDailyRulePojo);
        }catch(Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("删除规则发生异常");
        }
        return restResponse;
    }
    @EService("ES-EmployeeDailyRule-Update-T")
    @ManagerOperate("更新规则")
    public RestResponse updateEmployeeDailyRule(EmployeeDailyRuleUpdateParam EmployeeDailyRuleUpdateParam) throws Exception{
        logger.info("开始执行更新规则，请求标示为: "+RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse= new RestResponse();
        try{
            restResponse=this.employeeDailyRuleService.updateEmployeeDailyRule(EmployeeDailyRuleUpdateParam);
        }catch(Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("更新规则发生异常");
        }
        return restResponse;
    }
}
