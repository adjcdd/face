package com.grgbanking.framework.time.resource;

import com.grgbanking.framework.domains.App;
import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.time.feign.HelloClient;
import com.grgbanking.framework.time.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author wyf
 * Demo
 */
@RestController
public class HelloController {

    @Autowired
    @Qualifier("helloClient")
    HelloClient client;

    @Autowired
    @Qualifier("hystrixHelloClient")
    HelloClient hytrixClient;

    @Autowired
    @Qualifier("testService")
    private TestService testService;

    /**
     * 直接调用feign，feign会去调用eurekaService
     * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        App app = new App();
        return client.hello();
    }

    /**
     * 1、调用hytrix
     * 2、hytrix继承并调用feign
     * 3、feign会去调用eurekaService
     * */
    @RequestMapping(value = "/hytrix", method = RequestMethod.GET)
    @ResponseBody
    public String hytrixHello() {
        return hytrixClient.hello();
    }

    @RequestMapping(value = "/testDb", method = RequestMethod.GET)
    @ResponseBody
    public List<Test> testDb() throws Exception {
        return this.testService.getTestList();
    }

}
