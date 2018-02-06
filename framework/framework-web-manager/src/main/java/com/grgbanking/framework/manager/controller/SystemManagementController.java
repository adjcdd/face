package com.grgbanking.framework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mc on 2017/10/12.
 */
@Controller
public class SystemManagementController {
    @RequestMapping(value = "/toSystemUser", method = RequestMethod.GET)
    public String toSystemUser(Model model) throws Exception {
        return "systemManagement/user/ui/systemUser";
    }
    @RequestMapping(value = "/toSystemRole", method = RequestMethod.GET)
    public String toSystemRole(Model model) throws Exception {
        return "systemManagement/role/ui/systemRole";
    }
    @RequestMapping(value = "/toSystemMenu", method = RequestMethod.GET)
    public String toSystemMenu(Model model) throws Exception {
        return "systemManagement/menu/ui/systemMenu";
    }

}
