package com.grgbanking.framework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangweihua on 2018/1/18.
 */
@Controller
public class RuleManagementController {
    @RequestMapping(value="toRuleManagement",method = RequestMethod.GET)
    public String  toAttendanceRules(Model model) throws Exception{
        return "ruleManagement/ui/ruleManagement";
    }
}

