package com.grgbanking.framework.manager.service;

import com.alibaba.fastjson.JSONObject;
import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employeeDailyRule.pojo.EmployeeDailyRulePojo;
import com.grgbanking.framework.domains.employeeSign.param.EmployeeSignIdentifyParam;
import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;
import com.grgbanking.framework.domains.employeeSign.pojo.EmployeeSignPojo;
import com.grgbanking.framework.domains.employeeDaily.pojo.EmployeeDailyPojo;
import com.grgbanking.framework.domains.face.json.FaceIdentifyJson;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.pojo.MonitoringEmployeePojo;
import com.grgbanking.framework.manager.cache.SystemConfigCache;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.dwr.MessagePush;
import com.grgbanking.framework.manager.mapper.*;
import com.grgbanking.framework.util.base64.Base64Util;
import com.grgbanking.framework.util.http.OkHttp3Utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangweihua on 2018/1/3.
 */
@Service("monitoringService")
public class MonitoringService {
    private Logger logger = LoggerFactory.getLogger(MonitoringService.class);

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeSignMapper employeeSignMapper;
    @Autowired
    private EmployeeDailyMapper employeeDailyMapper;
    @Autowired
    private EmployeeDailyRuleMapper employeeDailyRuleMapper;

    @Transactional(rollbackFor = Exception.class)
    public RestResponse getMonitoring(EmployeeSignIdentifyParam employeeSignIdentifyParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 实时监控!");
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("实时监控成功");
            String platformSchema = SystemConfigCache.getConfigValue("platform.rest.schema");
            String platformHost = SystemConfigCache.getConfigValue("platform.rest.host");
            String platformPort = SystemConfigCache.getConfigValue("platform.rest.port");
            String url = platformSchema + "://" + platformHost + ":" + platformPort + "/grgbanking/biometrics/faceverify/user/identify-algorithm";
            Map<String, String> header = new HashMap<>();
            header.put("X-GB-Client-Id", SystemConfigCache.getConfigValue("manager.client.id"));
            String respStr = OkHttp3Utils.postJson(url, JSONObject.toJSONString(employeeSignIdentifyParam), header);
            logger.info("平台人脸识别1：N返回信息是：" + respStr);
            if (!StringUtils.isEmpty(respStr)) {
                restResponse = JSONObject.parseObject(respStr, RestResponse.class);
                if(restResponse.getResponseBody()!=null) {
                    FaceIdentifyJson faceIdentifyJson = JSONObject.parseObject(restResponse.getResponseBody().toString(), FaceIdentifyJson.class);
                    if (ErrorCode.SUCCESS.equals(restResponse.getResponseHeader().getErrorCode())) {
//                        try {
//                            EmployeePojo employeePojo = this.employeeMapper.getEmployeeInfo(emp);
//                             employeePojo.setImageBase64(Base64Util.encodeBase64Byte2Str(employeePojo.getImage()));
//                            restResponse.setResponseBody(employeePojo);
                            EmployeePojo emp = new EmployeePojo();
                            emp.setUid(faceIdentifyJson.getUid());
                            EmployeePojo employeePojo= this.employeeMapper.getEmployeeInfo(emp);//根据uid查找数据库是否有此人
                            if(employeePojo!=null){
                                EmployeeSignPojo employeeSignPojo=new EmployeeSignPojo();
                                employeeSignPojo.setUid(faceIdentifyJson.getUid());
                                employeeSignPojo.setClock_in_time(new Date());
                                employeeSignPojo.setSign(1);
                                employeeSignPojo.setName(employeePojo.getName());
                                String imageBase64=employeeSignIdentifyParam.getImage();
                                employeeSignPojo.setImageBase64(imageBase64);
                                employeeSignPojo.setImage(Base64Util.decodeBase64(imageBase64));
                                employeeSignPojo.setDevice_no(employeeSignIdentifyParam.getDevice_no());
                                this.employeeSignMapper.addEmployeeSign(employeeSignPojo);//添加比对成功数据

                                EmployeeDailyPojo employeeDailyPojo=new EmployeeDailyPojo();
                                BeanUtils.copyProperties(employeeSignPojo,employeeDailyPojo);

                                employeeDailyPojo.setDept(employeePojo.getDept());
                                List<EmployeeDailyPojo> employeeDailyPojos=this.employeeDailyMapper.getEmployeeDailyList(employeeDailyPojo);//根据条件查找此人是不是第一次打卡
                                //根据部门查找考勤规则
                                EmployeeDailyRulePojo empRulePojo=new EmployeeDailyRulePojo();
                                empRulePojo.setDept(employeePojo.getDept());
                                EmployeeDailyRulePojo employeeDailyRulePojo=null;
                                try{
                                     employeeDailyRulePojo=this.employeeDailyRuleMapper.getEmployeeDailyRuleByDept(empRulePojo);
                                    if(employeeDailyRulePojo!=null){
//                                        MessagePush messagePush= new MessagePush();
//                                        messagePush.send(employeeSignPojo.toString());
                                        String check_in_timeS= employeeDailyRulePojo.getCheck_in_time();
                                        String check_out_timeS=employeeDailyRulePojo.getCheck_out_time();
                                        if(employeeDailyPojos.size()==0 ||employeeDailyPojos==null){//第一次打卡
                                            //employeeDailyPojo.setId(null);
                                            employeeDailyPojo.setCheck_in_time(employeeSignPojo.getClock_in_time());
                                            Date check_in_time=employeeDailyPojo.getCheck_in_time();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String in_time=sdf.format(check_in_time);
                                            String year=in_time.substring(0,11);
                                            String ruleTime=year+check_in_timeS;
                                            String outTime=year+check_out_timeS;
                                            long oTime=sdf.parse(outTime).getTime();
                                            long time=sdf.parse(in_time).getTime();
                                            long test=sdf.parse(ruleTime).getTime();
                                            //如果打卡时间晚于下班时间则忘记上班打卡
                                            if(time>oTime){
                                                employeeDailyPojo.setFlag(5);//没有打上班卡

                                            }
//                                          根据打卡时间进行上班比对
                                            else if(time>test){//迟到
                                                employeeDailyPojo.setFlag(2);//迟到标记
                                               // long times=(time-test)/(1000 * 60);//迟到多少分钟
                                                employeeDailyPojo.setLater("是");
                                                Long times = time - test;//早退多少分钟
                                                employeeDailyPojo.setLater_time(times.doubleValue() / (1000 * 60));
                                            }else{
                                                employeeDailyPojo.setFlag(1);//上班正常标记
                                                employeeDailyPojo.setLater("否");
                                            }
                                            this.employeeDailyMapper.addEmployeeDaily(employeeDailyPojo);//根据需要是每次签到都显示还是只显示一次图片
                                        } else{
                                            //判断是否是最后一次打卡  如果不是
                                            //employeeDailyPojo.setCheck_in_time(employeeSignPojo.getClock_in_time());
                                            // Date check_in_time=employeeDailyPojo.getCheck_in_time();
                                            // DateFormat df = new SimpleDateFormat("hhmmss");
                                            employeeDailyPojo.setLater("否");
                                            employeeDailyPojo.setFlag(0);
                                            //之后多次打卡修改flag标记
                                            this.employeeDailyMapper.editEmployeeDaily(employeeDailyPojo);
                                            //如果是 在根据打卡时间进行下班时间比对
//                                            e.setFlag(0);
//                                          e.setCheck_out_time(employeeSignPojo.getClock_in_time());
//                                          this.employeeDailyMapper.editEmployeeDaily(e);
                                        }
                                        //this.employeeDailyMapper.addEmployeeDaily(employeeDailyPojo);//根据需要是每次签到都显示还是只显示一次图片
                                        MessagePush messagePush= new MessagePush();
                                        messagePush.send(employeeSignPojo.toString());
                                        restResponse.setResponseBody(employeeSignPojo);
                                        restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                                        restResponse.getResponseHeader().setMessage("签到成功");
                                    }else{
                                        MessagePush messagePush= new MessagePush();
                                        messagePush.send(imageBase64);
                                        restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                                        restResponse.getResponseHeader().setMessage("部门为空，查找失败");
                                    }
                                }catch(Exception e){
                                    logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "根据部门查找规则异常", e);
                                    e.printStackTrace();
                                }

                            }else{
                                EmployeeSignPojo employeeSignPojo=new EmployeeSignPojo();
                                employeeSignPojo.setClock_in_time(new Date());
                                employeeSignPojo.setSign(0);
                                String imageBase64=employeeSignIdentifyParam.getImage();
                                employeeSignPojo.setImageBase64(imageBase64);
                                employeeSignPojo.setImage(Base64Util.decodeBase64(imageBase64));
                                this.employeeSignMapper.addEmployeeSign(employeeSignPojo);
                                MessagePush messagePush= new MessagePush();
                                messagePush.send(imageBase64);
                                restResponse.setResponseBody(employeeSignPojo);
                                restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                                restResponse.getResponseHeader().setMessage("无此用户");
                            }
                        //EmployeeDailyPojo e=this.employeeDailyMapper.getEmployeeDaily(employeeDailyPojo);
                        //  employeeDailyPojo.setFlag(1);
                        //判断是否签到
                    }
                }else{
                    EmployeeSignPojo employeeSignPojo=new EmployeeSignPojo();
                    employeeSignPojo.setClock_in_time(new Date());
                    employeeSignPojo.setSign(0);
                    String imageBase64=employeeSignIdentifyParam.getImage();
                    employeeSignPojo.setImageBase64(imageBase64);
                    employeeSignPojo.setImage(Base64Util.decodeBase64(imageBase64));
                    this.employeeSignMapper.addEmployeeSign(employeeSignPojo);
                    MessagePush messagePush= new MessagePush();
                    messagePush.send(imageBase64);
                    restResponse.setResponseBody(employeeSignPojo);
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                    restResponse.getResponseHeader().setMessage("比对失败");
                }
            } else {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                restResponse.getResponseHeader().setMessage("实时监控失败");
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "实时监控出现异常", e);
            e.printStackTrace();
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse getLastCheckTime() throws  Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 查询下班打卡时间成功!");
        RestResponse restResponse = new RestResponse();
        try {
            //在比对表中 获取每个员工最后一次打卡记录
            List<EmployeeSignPojo> employeeSignPojoList=this.employeeSignMapper.getSignClockLastime();
            EmployeeDailyPojo employeeDailyPojo=new EmployeeDailyPojo();
            EmployeeDailyRulePojo employeeDailyRulePojo=new EmployeeDailyRulePojo();
            EmployeeSignPojo employeeSignPojo=new EmployeeSignPojo();
            for(int i=0;i<employeeSignPojoList.size();i++){
                employeeSignPojo=employeeSignPojoList.get(i);
                BeanUtils.copyProperties(employeeSignPojo,employeeDailyPojo);
                //通过uid获取签到人员
                employeeDailyPojo=this.employeeDailyMapper.getEmployeeDaily(employeeDailyPojo);
                Integer flag=employeeDailyPojo.getFlag();
                if(flag==1||flag==2){
                    //没有打下班卡
                    employeeDailyPojo.setCheck_out_time(employeeDailyPojo.getCheck_in_time());//暂定为上班打卡时间
                    employeeDailyPojo.setFlag(6);//漏打下班卡
                    this.employeeDailyMapper.editEmployeeDaily(employeeDailyPojo);
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                    restResponse.getResponseHeader().setMessage("没有打下班卡");
                }else if(flag==0){
                    employeeDailyPojo.setCheck_out_time(employeeSignPojo.getClock_in_time());//更新最后一次签到时间
                    //根据比对表数据获取部门并设置给规则表
                    employeeDailyRulePojo.setDept(employeeDailyPojo.getDept());
                    employeeDailyRulePojo = this.employeeDailyRuleMapper.getEmployeeDailyRuleByDept(employeeDailyRulePojo);
                    String check_out_timeS = employeeDailyRulePojo.getCheck_out_time();
                    //获取签到表下班时间
                    Date check_out_time = employeeDailyPojo.getCheck_out_time();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String out_time = sdf.format(check_out_time);
                    String year = out_time.substring(0, 11);
                    String ruleTime = year + check_out_timeS;
                    long time = sdf.parse(out_time).getTime();
                    long test = sdf.parse(ruleTime).getTime();
                    if (time > test) {
                        employeeDailyPojo.setFlag(3);//下班正常标记
                        employeeDailyPojo.setEarly("否");
                    } else {
                        //早退
                        employeeDailyPojo.setFlag(4);//早退标记
                        employeeDailyPojo.setEarly("是");
                        Long times = test-time;//早退多少分钟
                        employeeDailyPojo.setEarly_time(times.doubleValue() / (1000 * 60));
                    }
                    this.employeeDailyMapper.editEmployeeDaily(employeeDailyPojo);
                    restResponse.setResponseBody(employeeSignPojo);
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                    restResponse.getResponseHeader().setMessage("下班签到成功");
                }else{
                    employeeDailyPojo.setCheck_out_time(employeeDailyPojo.getCheck_in_time());//暂定为上班打卡时间
                    this.employeeDailyMapper.editEmployeeDaily(employeeDailyPojo);
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
                    restResponse.getResponseHeader().setMessage("没有打上班卡");
                }
            }
        }catch (Exception e){
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询下班打卡时间出现异常", e);
            throw e;
        }
        return restResponse;
    }

   /* public RestResponse getSignList() throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 查询比对成功!");
        RestResponse restResponse = new RestResponse();
        try {
            List<EmployeeSignPojo> employeeSignPojoList = new ArrayList<EmployeeSignPojo>();
            employeeSignPojoList = this.employeeSignMapper.getSignList();
            for(int i=0;i<employeeSignPojoList.size();i++){
                employeeSignPojoList.get(i).setImageBase64(Base64Util.encodeBase64Byte2Str(employeeSignPojoList.get(i).getImage()));
            }
            restResponse.setResponseBody(employeeSignPojoList);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询比对成功出现异常", e);
            throw e;
        }
        return restResponse;
    }*/
    public RestResponse getSignFailList() throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 查询比对失败成功!");
        RestResponse restResponse = new RestResponse();
        try {
            List<EmployeeSignPojo> employeeSignPojoList = new ArrayList<EmployeeSignPojo>();
            employeeSignPojoList = this.employeeSignMapper.getSignFailList();
            for(int i=0;i<employeeSignPojoList.size();i++){
                employeeSignPojoList.get(i).setImageBase64(Base64Util.encodeBase64Byte2Str(employeeSignPojoList.get(i).getImage()));
            }
            restResponse.setResponseBody(employeeSignPojoList);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询比对失败列表出现异常", e);
            throw e;
        }
        return restResponse;
    }
    public RestResponse getDailyList() throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 查询签到列表!");
        RestResponse restResponse = new RestResponse();
         try{
            List<EmployeeDailyPojo> employeeDailyPojos=new ArrayList<EmployeeDailyPojo>();
            employeeDailyPojos=this.employeeDailyMapper.getCurrentDailyList();
              for(int i=0;i<employeeDailyPojos.size();i++){
                 employeeDailyPojos.get(i).getEmployeeSignPojo().setImageBase64(Base64Util.encodeBase64Byte2Str(employeeDailyPojos.get(i).getEmployeeSignPojo().getImage()));
             }
             restResponse.setResponseBody(employeeDailyPojos);
             restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
             restResponse.getResponseHeader().setMessage("查询成功");
        }catch (Exception e){
             logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询签到列表出现异常", e);
             throw e;
        }
        return restResponse;
    }
}

