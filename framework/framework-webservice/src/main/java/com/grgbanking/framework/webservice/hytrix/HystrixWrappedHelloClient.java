package com.grgbanking.framework.webservice.hytrix;

import com.grgbanking.framework.webservice.feign.HelloClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author wyf
 */
@Service("hystrixHelloClient")
public class HystrixWrappedHelloClient {

    @Autowired
    @Qualifier("helloClient")
    private HelloClient feignHelloClient;

    @HystrixCommand(groupKey = "helloGroup", fallbackMethod = "fallBackCall")
    public String hello() {
        return this.feignHelloClient.hello();
    }

    public String fallBackCall() {
        String fallback = ("FAILED SERVICE CALL! - FALLING BACK");
        return fallback;
    }
}
