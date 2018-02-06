package com.grgbanking.framework.bd.face.mapper;

import com.grgbanking.framework.db.mapper.BaseMapper;
import com.grgbanking.framework.domains.Test;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wyf on 2017/5/25.
 */
@Service("testMapper")
public interface TestMapper extends BaseMapper {

    List<Test> getTestList();

    void insertTest1(Test test);

    void insertTest2(Test test);
}
