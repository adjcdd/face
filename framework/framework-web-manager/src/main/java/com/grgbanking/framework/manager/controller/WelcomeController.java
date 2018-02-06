package com.grgbanking.framework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wyf on 2017/7/19.
 */
@Controller
public class WelcomeController {

    @RequestMapping(value = {"/", "", "login"}, method = RequestMethod.GET)
    public String login() throws Exception {
        return "login/ui/login";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() throws Exception {
        return "index/ui/index";
    }

}
