package com.grgbanking.framework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhangweihua on 2018/1/3.
 */
@Controller
public class MonitoringController {
    @RequestMapping(value = "/toMonitoring", method = RequestMethod.GET)
    public String toDwr(Model model) throws Exception {
        return "monitoring/ui/monitoring";
    }
}
