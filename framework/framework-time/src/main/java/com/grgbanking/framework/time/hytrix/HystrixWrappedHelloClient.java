package com.grgbanking.framework.time.hytrix;

import com.grgbanking.framework.time.feign.HelloClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author wyf
 */
@Service("hystrixHelloClient")
public class HystrixWrappedHelloClient implements HelloClient {

    @Autowired
    @Qualifier("helloClient")
    private HelloClient feignHelloClient;

    @Override
    @HystrixCommand(groupKey = "helloGroup", fallbackMethod = "fallBackCall")
    public String hello() {
        return this.feignHelloClient.hello();
    }

    public String fallBackCall() {
        String fallback = ("FAILED SERVICE CALL! - FALLING BACK");
        return fallback;
    }
}
