package com.grgbanking.framework.manager.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyf on 2017/8/31.
 * 系统配置缓存
 */
public class SystemConfigCache {

    private static Map<String, String> systemConfig = new HashMap<>();

    public static void putConfig(String key, String value){
        systemConfig.put(key, value);
    }

    public static String getConfigValue(String key){
        return systemConfig.get(key);
    }

}
