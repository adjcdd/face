package com.grgbanking.framework.rest.service;

import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.rest.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */
@Service("testService")
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Test> getTestList(){
        return this.testMapper.getTestList();
    }
}
