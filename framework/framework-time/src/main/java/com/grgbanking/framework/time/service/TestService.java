package com.grgbanking.framework.time.service;

import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.time.mapper.TestMapper;
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
        return this.testMapper.getTestList();
    }

}
