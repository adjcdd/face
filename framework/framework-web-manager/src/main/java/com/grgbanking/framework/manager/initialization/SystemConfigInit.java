package com.grgbanking.framework.manager.initialization;

import com.grgbanking.framework.manager.cache.SystemConfigCache;
import com.grgbanking.framework.util.rsa.EncryptionAlgorithm;
import com.grgbanking.framework.util.rsa.Suggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by wyf on 2017/8/31.
 */
@Service("systemConfigInit")
public class SystemConfigInit implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(SystemConfigInit.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        this.getSystemProperties();
    }

    private void getSystemProperties(){
        logger.info("开始初始化系统配置参数...");
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("system-config.properties");
            Properties prop = new Properties();
            prop.load(new InputStreamReader(inputStream, "UTF-8"));
            Enumeration propKeys = prop.propertyNames();
            while(propKeys.hasMoreElements()){
                String propKey = (String)propKeys.nextElement();
                String propValue = prop.getProperty(propKey);
                logger.info(propKey + " ==================== " + propValue);
                SystemConfigCache.putConfig(propKey.trim(), propValue.trim());
            }
        }catch (Exception e){
            logger.error("获取系统属性异常", e);
        }
    }

}
