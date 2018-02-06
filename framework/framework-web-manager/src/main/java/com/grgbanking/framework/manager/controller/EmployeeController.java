package com.grgbanking.framework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangweihua on 2017/12/18.
 */
@Controller
public class EmployeeController {
    @RequestMapping(value = "/toEmployee", method = RequestMethod.GET)
    public String toSystemRegist(Model model) throws Exception {
        return "employee/feature/ui/employee";
    }

}
