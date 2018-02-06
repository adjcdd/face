package com.grgbanking.framework.webservice.service.impl;

import com.grgbanking.framework.domains.App;
import com.grgbanking.framework.webservice.hytrix.HystrixWrappedHelloClient;
import com.grgbanking.framework.webservice.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/6.
 */
@Component("appServiceImpl")
public class AppServiceImpl implements AppService {

    @Autowired
    @Qualifier("hystrixHelloClient")
    HystrixWrappedHelloClient hytrixClient;

    @Override
    public String hello() {
        String msg = hytrixClient.hello();
        System.out.println("hello: " + msg);
        return "hello: " + msg;
    }

    @Override
    public String helloToSomeBody(String name) {
        String msg = hytrixClient.hello();
        System.out.println("helloToSomeBody: " + msg + ", name: " + name);
        return "helloToSomeBody: " + msg + ", name: " + name;
    }

    @Override
    public App helloToApp(App app) {
        System.out.println(app);
        app.setId(555);
        app.setName(app.getName() + ":NewName");
        app.setPassword(app.getPassword() + ":NewPassword");
        return app;
    }
}
