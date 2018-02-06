package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.MonitoringService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangweihua on 2018/1/4.
 */
public class MonitoringEService {
    private Logger logger = LoggerFactory.getLogger(MonitoringService.class);
    private MonitoringService monitoringService = BeanFactoryConfig.getBean("monitoringService");

//    @EService("ES-Monitoring-Info-T")
//    @ManagerOperate("实时监控")
//    public RestResponse getEmployeeList(EmployeeSignIdentifyParam employeeSignIdentifyParam) throws Exception {
//        logger.info("开始执行实时监控服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
//        RestResponse restResponse = new RestResponse();
//        try {
//            restResponse = this.monitoringService.getMonitoring(employeeSignIdentifyParam);
//        }catch (Exception e){
//            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
//            restResponse.getResponseHeader().setMessage("实时监控发生异常");
//        }
//        return restResponse;
//    }
    /*@EService("ES-Monitoring-List-T")
    @ManagerOperate("查询比对成功")
    public RestResponse getEmployeeSignList()throws Exception{
        logger.info("开始执行查询比对成功服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringService.getSignList();
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询比对成功发生异常");
        }
        return restResponse;
    }*/
    @EService("ES-Monitoring-FailList-T")
    @ManagerOperate("查询比对失败成功")
    public RestResponse getEmployeeSignFailList()throws Exception{
        logger.info("开始执行查询比对失败服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringService.getSignFailList();
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询比对失败发生异常");
        }
        return restResponse;
    }
    @EService("ES-Monitoring-DailyList-T")
    @ManagerOperate("查询签到列表")
    public RestResponse getDailyList()throws Exception{
        logger.info("开始执行查询签到列表服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringService.getDailyList();
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询签到列表发生异常");
        }
        return restResponse;
    }
}
