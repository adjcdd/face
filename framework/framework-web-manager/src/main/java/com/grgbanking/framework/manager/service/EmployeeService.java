package com.grgbanking.framework.manager.service;

import com.alibaba.fastjson.JSONObject;
import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employee.param.EmployeeAddParam;
import com.grgbanking.framework.domains.employee.param.EmployeeListParam;
import com.grgbanking.framework.domains.employee.param.EmployeeUpdateParam;
import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.manager.cache.SystemConfigCache;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.dwr.MessagePush;
import com.grgbanking.framework.manager.dwr.SendMessage;
import com.grgbanking.framework.manager.mapper.EmployeeMapper;
import com.grgbanking.framework.util.base64.Base64Util;
import com.grgbanking.framework.util.http.OkHttp3Utils;
import com.grgbanking.framework.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zhangweihua on 2017/12/18.
 */
@Service("employeeService")
public class EmployeeService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    public RestResponse getEmployeeList(EmployeeListParam employeeListParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询员工列表!");
        RestResponse restResponse = new RestResponse();
        try {
            employeeListParam.setCurrentCount((employeeListParam.getPageNo() - 1) * employeeListParam.getPageSize());
            List<EmployeePojo> employeePojoList = new ArrayList<EmployeePojo>();
            long totalCount = 0;
            employeePojoList = this.employeeMapper.getEmployeeList(employeeListParam);
            totalCount = this.employeeMapper.getEmployeeCount(employeeListParam);

            PaginationJson page = new PaginationJson();
            page.setPageNo(employeeListParam.getPageNo());
            page.setPageSize(employeeListParam.getPageSize());
            page.setData(employeePojoList);
            page.setTotalCount(totalCount);
            page.setTotalPage(PageUtil.calTotalPage(totalCount, employeeListParam.getPageSize()));
            restResponse.setResponseBody(page);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询员工列表出现异常", e);
            throw e;
        }
        return restResponse;
    }
    public RestResponse getEmployeeInfo(EmployeePojo employeePojo) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询员工列表!");
        RestResponse restResponse = new RestResponse();
        try {
            employeePojo = this.employeeMapper.getEmployeeInfo(employeePojo);
            employeePojo.setImageBase64(Base64Util.encodeBase64Byte2Str(employeePojo.getImage()));
            restResponse.setResponseBody(employeePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
/*
            SendMessage sendMessage=new SendMessage();
            sendMessage.sendMsg(employeePojo.getImageBase64());
*/

        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询员工列表出现异常", e);
            throw e;
        }
        return restResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    public RestResponse addEmployee(EmployeeAddParam employeeAddParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增员工!");
        RestResponse restResponse = new RestResponse();
        try{
            String platformSchema = SystemConfigCache.getConfigValue("platform.rest.schema");
            String platformHost = SystemConfigCache.getConfigValue("platform.rest.host");
            String platformPort = SystemConfigCache.getConfigValue(  "platform.rest.port");
            String url=platformSchema + "://" +platformHost + ":" +platformPort + "/grgbanking/biometrics/faceverify/user/add";
            Map<String,String> header=new HashMap<>();
            header.put("X-GB-Client-Id",SystemConfigCache.getConfigValue("manager.client.id"));
            String respStr = OkHttp3Utils.postJson(url, JSONObject.toJSONString(employeeAddParam),header);
            logger.info("平台注册用户返回信息是："+ respStr);
             if(!StringUtils.isEmpty(respStr)){
                restResponse = JSONObject.parseObject(respStr,RestResponse.class);
            }else {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                restResponse.getResponseHeader().setMessage("调用服务失败");
            }
        }catch(Exception e){
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增员工出现异常", e);
            e.printStackTrace();
        }
        try {
            if(ErrorCode.SUCCESS.equals(restResponse.getResponseHeader().getErrorCode())){
                EmployeePojo employeePojo = new EmployeePojo();
                String base64String=employeeAddParam.getImage();
                BeanUtils.copyProperties(employeeAddParam, employeePojo);
                byte[] image= Base64Util.decodeBase64(base64String);
                employeePojo.setImage(image);
                Date date=new Date();
                employeePojo.setCreateTime(date);
                this.employeeMapper.addEmployee(employeePojo);
//                MessagePush messagePush= new MessagePush();
//                messagePush.send(base64String);
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("新增员工成功");
             }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "服务器调用出现异常", e);
            throw e;
        }
        return restResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    public RestResponse deleteEmployee(EmployeePojo employeePojo) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始删除员工!");
        RestResponse restResponse = new RestResponse();
        try{
            String platformSchema = SystemConfigCache.getConfigValue("platform.rest.schema");
            String platformHost = SystemConfigCache.getConfigValue("platform.rest.host");
            String platformPort = SystemConfigCache.getConfigValue(  "platform.rest.port");
            String url=platformSchema + "://" +platformHost + ":" +platformPort + "/grgbanking/biometrics/faceverify/user/delete";
            Map<String,String> header=new HashMap<>();
            header.put("X-GB-Client-Id",SystemConfigCache.getConfigValue("manager.client.id"));
            String respStr = OkHttp3Utils.postJson(url, JSONObject.toJSONString(employeePojo),header);
            logger.info("平台注册用户返回信息是："+ respStr);
            if(!StringUtils.isEmpty(respStr)){
                restResponse = JSONObject.parseObject(respStr,RestResponse.class);
            }else {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                restResponse.getResponseHeader().setMessage("调用服务失败");
            }
        }catch(Exception e){
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "删除员工出现异常", e);
            e.printStackTrace();
        }
        try {
            if(ErrorCode.SUCCESS.equals(restResponse.getResponseHeader().getErrorCode())){
                this.employeeMapper.deleteEmployee(employeePojo);
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("删除员工成功");
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "服务器调用出现异常", e);
            throw e;
        }
        return restResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    public RestResponse updateEmployee(EmployeeUpdateParam employeeUpdateParam) throws  Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新员工!");
        RestResponse restResponse = new RestResponse();
        /*try{
            String platformSchema = SystemConfigCache.getConfigValue("platform.rest.schema");
            String platformHost = SystemConfigCache.getConfigValue("platform.rest.host");
            String platformPort = SystemConfigCache.getConfigValue(  "platform.rest.port");
            String url=platformSchema + "://" +platformHost + ":" +platformPort + "/grgbanking/biometrics/faceverify/user/feature-update";
            Map<String,String> header=new HashMap<>();
            header.put("X-GB-Client-Id",SystemConfigCache.getConfigValue("manager.client.id"));
            String respStr = OkHttp3Utils.postJson(url, JSONObject.toJSONString(employeeUpdateParam),header);
            logger.info("平台更新用户返回信息是："+ respStr);
            if(!StringUtils.isEmpty(respStr)){
                restResponse = JSONObject.parseObject(respStr,RestResponse.class);
            }else {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                restResponse.getResponseHeader().setMessage("调用服务失败");
            }
        }catch(Exception e){
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新员工出现异常", e);
            e.printStackTrace();
        }
        if(ErrorCode.SUCCESS.equals(restResponse.getResponseHeader().getErrorCode())){*/
        try {

                EmployeePojo employeePojo = new EmployeePojo();
                String base64String=employeeUpdateParam.getImage();
                BeanUtils.copyProperties(employeeUpdateParam, employeePojo);
                byte[] image= Base64Util.decodeBase64(base64String);
                employeePojo.setImage(image);
                this.employeeMapper.updateEmployee(employeePojo);
              /*  MessagePush messagePush= new MessagePush();
                messagePush.send(employeePojo.getTelphone());*/
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("更新员工成功");

           // }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新员工出现异常", e);
            throw e;
        }
        return restResponse;
    }
}
