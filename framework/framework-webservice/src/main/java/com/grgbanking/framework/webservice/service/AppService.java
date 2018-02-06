package com.grgbanking.framework.webservice.service;

import com.grgbanking.framework.domains.App;

/**
 * Created by wyf on 2017/7/6.
 */
public interface AppService {

    String hello();

    String helloToSomeBody(String name);

    App helloToApp(App app);

}
