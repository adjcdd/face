package com.grgbanking.framework.rest.config;

import com.grgbanking.framework.util.exception.AppException;
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

    /**
     * 人脸识别算法
     * bd  百度
     * grg  自有算法
     */
    public static String faceArithmetic;

    /**
     * 服务节点编号
     */
    public static String serverNode;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment);
        try {
            this.getFaceArithmetic();
            this.getServerNode();
        }catch (AppException e){
            logger.error("读取配置文件异常", e);
        }

    }

    private void getFaceArithmetic() throws AppException {
        logger.info("开始读取人脸识别算法提供商...");
        faceArithmetic = propertyResolver.getProperty("recognize.face.arithmetic");
        if(null == faceArithmetic || "".equals(faceArithmetic)){
            logger.error("读取人脸识别算法提供商异常");
            throw new AppException("读取人脸识别算法提供商异常!");
        }
    }

    private void getServerNode(){
        logger.info("开始获取服务节点编号...");
        serverNode = propertyResolver.getProperty("rest.server.node");
        if(null == serverNode || "".equals(serverNode)){
            logger.error("当前服务未指定节点编号!");
            System.exit(0);
        }
        logger.info("当前节点编号是: " + serverNode);
    }

}
