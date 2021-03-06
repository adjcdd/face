package com.grgbanking.framework.manager.dispatcher;

/**
 * Created by wyf on 2017/8/10.
 * 保存每次请求的请求ID，使用ThreadLocal将变量在线程之间进行隔离，避免出现并发
 */
public class RequestIdentifierLocalHolder {

    private static final ThreadLocal<String> value = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "";
        }
    };

    public static void setRequestIdentifier(String requestIdentifier){
        value.set(requestIdentifier);
    }

    public static String getRequestIdentifier(){
        return value.get();
    }


}
