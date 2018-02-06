package com.grgbanking.framework.bd.face.service;

import com.grgbanking.framework.bd.face.mapper.TestMapper;
import com.grgbanking.framework.domains.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */
@Service("testService")
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    @Qualifier("testMapper")
    private TestMapper testMapper;

    public List<Test> getTestList(){
        return this.testMapper.getTestList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertTest(){
        Test test1 = new Test();
        test1.setId(13);
        test1.setName("name10");
        test1.setAge(12);
        this.testMapper.insertTest1(test1);
        Test test2 = new Test();
        test2.setId(14);
        test2.setName("name11");
        test2.setAge(16);
        this.testMapper.insertTest2(test2);
    }

}
