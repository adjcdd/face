package com.grgbanking.framework.webservice.gateway;

import com.grgbanking.framework.domains.App;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by wyf on 2017/7/10.
 */
@WebService
public interface AppServiceGateWay {

    @WebMethod
    String hello();

    @WebMethod
    String helloToSomeBody(String name);

    @WebMethod
    App helloToApp(App app);

}
