package com.grgbanking.framework.manager.resource;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employeeSign.param.EmployeeSignIdentifyParam;
import com.grgbanking.framework.domains.manager.data_dictionary.pojo.DataDictionaryPojo;
import com.grgbanking.framework.manager.service.DataDictionaryService;
import com.grgbanking.framework.manager.service.MonitoringEmployeeService;
import com.grgbanking.framework.manager.service.MonitoringService;
import com.grgbanking.framework.util.base64.Base64Util;
import com.grgbanking.framework.util.http.OkHttp3Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


/**
 * Created by zhangweihua on 2018/1/10.
 */
@Controller
public class MonitoringResource {
    private Logger logger = LoggerFactory.getLogger(MonitoringService.class);
    @Autowired
    @Qualifier("monitoringService")
    private MonitoringService monitoringService;
    @RequestMapping(value = "/monitoring", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse monitoring(@RequestBody EmployeeSignIdentifyParam employeeSignIdentifyParam) throws Exception {
        logger.info("开始实时监控...");
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringService.getMonitoring(employeeSignIdentifyParam);

        } catch (Exception e) {
            logger.error("实时监控异常", e);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }

    @Autowired
    @Qualifier("monitoringEmployeeService")
    private MonitoringEmployeeService monitoringEmployeeService;
    @RequestMapping(value = "/addMonitoringEmployee", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse addMonitoringEmployee() throws Exception {
        logger.info("开始获取新增个人实时考勤报表数据监控...");
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringEmployeeService.addMonitoringEmployee();
        } catch (Exception e) {
            logger.error("获取新增个人实时考勤报表数据异常", e);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }
    @RequestMapping(value = "/getLastTime", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse getLastCheckTime() throws Exception {
        logger.info("开始获取下班打卡数据监控...");
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.monitoringService.getLastCheckTime();

        } catch (Exception e) {
            logger.error("获取下班打卡数据异常", e);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }
    private Logger log = LoggerFactory.getLogger(DataDictionaryService.class);
    @Autowired
    @Qualifier("dataDictionaryService")
    private DataDictionaryService dataDictionaryService;
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse getAll() throws  Exception{
        log.info("查询字典");
        RestResponse restResponse=new RestResponse();
        try{
            restResponse=this.dataDictionaryService.getAllDataDictionary();
        }catch (Exception e){
            log.error("实时监控异常", e);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }

/*        public static void main(String[] args){
        String url = "http://192.168.1.22:8088/face/addMonitoringEmployee";
        String param = "";
        String respStr = OkHttp3Utils.postJson(url, param, null);
        System.out.print(respStr);

    }*/
/*
    public static void main(String[] args){
        String url = "http://192.168.1.22:8088/face/getLastTime";
        String param = "";
        String respStr = OkHttp3Utils.postJson(url, param, null);
        System.out.print(respStr);

    }
*/


    public static void main(String[] args){

        File file = new File("G:\\123\\4.jpg");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            byte[] buffer = bos.toByteArray();
            fis.close();
            bos.close();
            EmployeeSignIdentifyParam employeeSignIdentifyParam = new EmployeeSignIdentifyParam();
            employeeSignIdentifyParam.setDevice_no("001");
            employeeSignIdentifyParam.setImage(Base64Util.encodeBase64Byte2Str(buffer));
            String url = "http://192.168.1.22:8088/face/monitoring";
            String param = "{\"image\":\"" + employeeSignIdentifyParam.getImage() + "\",\"device_no\":\"" + employeeSignIdentifyParam.getDevice_no() +"\"}";
            String respStr = OkHttp3Utils.postJson(url, param, null);
            System.out.print(respStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
