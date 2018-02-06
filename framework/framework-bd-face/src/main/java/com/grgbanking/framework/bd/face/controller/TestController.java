package com.grgbanking.framework.bd.face.controller;

import com.grgbanking.framework.bd.face.service.TestService;
import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.util.rsa.CryptoAlgorithmFactory;
import com.grgbanking.framework.util.rsa.Encrypto;
import com.grgbanking.framework.util.rsa.RSAEncryptoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */
@RestController
@Api(value = "测试数据库", description = "测试数据库")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    @Qualifier("testService")
    private TestService testService;

    /**
     * 直接调用feign，feign会去调用eurekaService
     * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="测试接口", notes="HelloWorld")
    public String helloBd() {
        return "hello-bd";
    }

    @RequestMapping(value = "/testdb", method = RequestMethod.GET)
    @ApiOperation(value="测试接口", notes="testdb")
    public List<Test> hello() {
        logger.info("开始查询数据...");
        List<Test> testList = this.testService.getTestList();
        return testList;
    }

    @RequestMapping(value = "/test-insert", method = RequestMethod.GET)
    @ApiOperation(value="测试插入事务接口", notes="测试插入事务接口")
    public void insertTest(){
        this.testService.insertTest();
    }

}
