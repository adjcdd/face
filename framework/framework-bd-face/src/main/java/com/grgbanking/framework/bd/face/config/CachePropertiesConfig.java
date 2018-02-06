package com.grgbanking.framework.bd.face.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by wyf on 2017/5/27.
 * 读取配置文件中的配置信息到内存中
 */
@Configuration
public class CachePropertiesConfig implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(CachePropertiesConfig.class);

    private RelaxedPropertyResolver propertyResolver;

    public static String groupId;
//    public static String topNum;
//    public static Double threshold; //阀值
    public static String registerURL;  //注册
    public static String updateURL;  //更新
    public static String deleteURL;  //删除
    public static String getURL;  //查询
    public static String verifyURL;  //认证
    public static String identifyURL;  //1:N识别
    public static String matchURL;  //两两比对
    public static String detectURL; //人脸检测

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "bd.face.restapi.");
        this.getInitBdFaceURL();
    }

    /**
     * 初始化百度人脸识别的验证URL
     */
    private void getInitBdFaceURL(){
        logger.info("开始读取百度人脸URL配置...");
        groupId = propertyResolver.getProperty("groupId");
//        topNum = propertyResolver.getProperty("topNum");
//        threshold = Double.parseDouble(propertyResolver.getProperty("threshold"));
        registerURL = propertyResolver.getProperty("register");
        updateURL = propertyResolver.getProperty("update");
        deleteURL = propertyResolver.getProperty("delete");
        getURL = propertyResolver.getProperty("get");
        verifyURL = propertyResolver.getProperty("verify");
        identifyURL = propertyResolver.getProperty("identify");
        matchURL = propertyResolver.getProperty("match");
        detectURL = propertyResolver.getProperty("detect");
    }


}
