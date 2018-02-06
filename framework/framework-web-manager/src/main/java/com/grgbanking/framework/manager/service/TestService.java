package com.grgbanking.framework.manager.service;

import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.manager.mapper.TestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("testService")
public class TestService {

    private Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private TestMapper testMapper;

    public List<Test> getTestList(){
        logger.info("Start to get list of test!");
        logger.error("出现错误");
//        int i = 1/0;
        return this.testMapper.getTestList();
    }

}
