package com.grgbanking.framework.rest.controller;

import com.grgbanking.framework.domains.App;
import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.rest.feign.HelloClient;
import com.grgbanking.framework.rest.hytrix.HystrixWrappedHelloClient;
import com.grgbanking.framework.rest.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author wyf
 * Demo
 */
@RestController
@Api(value = "测试", description = "swagger测试")
public class HelloController {

    @Autowired
    @Qualifier("helloClient")
    HelloClient client;

    @Autowired
    @Qualifier("hystrixHelloClient")
    HystrixWrappedHelloClient hytrixClient;

    @Autowired
    private TestService testService;


    /**
     * 直接调用feign，feign会去调用eurekaService
     * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="测试接口", notes="HelloWorld")
    public RestResponse hello() {
        RestResponse restResponse = new RestResponse();
        restResponse.setResponseBody(client.hello());
        return restResponse;
    }

    /**
     * 1、调用hytrix
     * 2、hytrix继承并调用feign
     * 3、feign会去调用eurekaService
     * */
    @RequestMapping(value = "/hytrix", method = RequestMethod.GET)
    @ApiOperation(value="测试熔断接口", notes="HelloWorldHystrix")
    public RestResponse hytrixHello() {
        RestResponse restResponse = new RestResponse();
        restResponse.setResponseBody(hytrixClient.hello());
        return restResponse;
    }

    @RequestMapping(value = "/testdb", method = RequestMethod.GET)
    @ApiOperation(value="测试接口", notes="testdb")
    public RestResponse testDb() {
        RestResponse restResponse = new RestResponse();
        List<Test> testList = this.testService.getTestList();
        restResponse.setResponseBody(testList);
        return restResponse;
    }

}
