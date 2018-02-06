package com.grgbanking.framework.manager.initialization;

import com.grgbanking.framework.manager.cache.EsServiceCache;
import com.grgbanking.framework.util.annotations.EService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyf on 2017/8/7.
 * 服务注册器
 * 在系统启动时进行执行 ，将所有的服务注册进内存中
 */
@Service("esServiceRegister")
public class EsServiceRegister implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(EsServiceRegister.class);

    public void afterPropertiesSet() throws Exception {
        logger.info("开始服务注册...");
        String esPackage = "com.grgbanking.framework.manager.eservice";
        List<String> classNames = getClassName(esPackage);
        for (String className : classNames) {
            this.getEServiceName(className);
        }
    }

    private void getEServiceName(String className) throws Exception{
        Class clazz = Class.forName(className);
        // 获取所有方法
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            EService eService = method.getAnnotation(EService.class);
            // 不属于服务方法，直接跳过
            if(null == eService){
                continue;
            }
            String serviceName = eService.value();
            String methodName = method.getName();
            if("".equals(serviceName)){
                //throw new RuntimeException(className + "." + method.getName() + "() did not specify the eservice-name");
                logger.error(className + "." + methodName + "() did not specify the eservice-name");
                logger.error("JVM will exit because of pre-error!");
                System.exit(1);
            }
            // 将此服务加载到内存中
            EsServiceCache.registerService(serviceName, className, methodName);
        }
    }

    private List<String> getClassName(String packageName) {
//        String filePath = ClassLoader.getSystemResource("/").getPath() + packageName.replace(".", "\\");
        Class clazz = this.getClass();
        ClassLoader loader = clazz.getClassLoader();
        URL url = loader.getResource(packageName.replace(".", "\\"));
        String filePath = url.getFile();
        List<String> fileNames = getClassName(filePath, null);
        return fileNames;
    }

    private static List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("\\", ".");
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }

}
