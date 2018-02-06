package com.grgbanking.framework.time.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wyf on 2017/7/18.
 */
@Controller
public class SampleController {

    @RequestMapping(value = "/toSample1", method = RequestMethod.GET)
    public String toSample1() throws Exception {
        return "sample/sample1";
    }

    @RequestMapping(value = "/toSample2", method = RequestMethod.GET)
    public String toSample2() throws Exception {
        return "sample/sample2";
    }

}
