package com.grgbanking.framework.time.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeResource {
	
	@RequestMapping("/index")
	public String welcome(ModelMap map) {
		map.put("message", "welcome");
		return "index";
	}
 
}