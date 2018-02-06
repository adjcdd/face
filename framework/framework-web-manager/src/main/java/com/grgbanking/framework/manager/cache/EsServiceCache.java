package com.grgbanking.framework.manager.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyf on 2017/8/7.
 * 保存系统服务信息的缓存
 */
public class EsServiceCache {

    private static Logger logger = LoggerFactory.getLogger(EsServiceCache.class);

    private static Map<String, EServiceClassMethod> enterpriseService = new HashMap();

    public static String getClassName(String serviceName){
        return enterpriseService.get(serviceName).getClassName();
    }

    public static String getMethodName(String serviceName){
        return enterpriseService.get(serviceName).getMethodName();
    }

    public static void registerService(String serviceName, String className, String methodName){
        logger.info("Start to register service: " + serviceName + ", ClassName: " + className + ", MethodName: " + methodName);
        EServiceClassMethod serviceClassMethod = EServiceClassMethod.getInstance();
        serviceClassMethod.setClassName(className);
        serviceClassMethod.setMethodName(methodName);
        if(!enterpriseService.containsKey(serviceName)){
            enterpriseService.put(serviceName, serviceClassMethod);
        }else{
            logger.error("服务 " + serviceName + " 已存在");
            System.exit(1);
        }
    }

    static class EServiceClassMethod{

        private String className;

        private String methodName;

        public static EServiceClassMethod getInstance(){
            return new EServiceClassMethod();
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }

}


