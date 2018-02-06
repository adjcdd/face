package com.grgbanking.framework.manager.resource;

import com.grgbanking.framework.domains.App;
import com.grgbanking.framework.domains.Test;
import com.grgbanking.framework.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestResource {

	@Autowired
	@Qualifier("testService")
	private TestService testService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public App test() throws Exception {
		App app = new App();
		app.setId(1);
		app.setName("王亚菲");
		app.setPassword("password");
		return app;
	}

	@RequestMapping(value = "/testDb", method = RequestMethod.GET)
	@ResponseBody
	public List<Test> testDb() throws Exception {
		return this.testService.getTestList();
	}


}
