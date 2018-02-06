package com.grgbanking.framework.webservice.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wyf
 */
@FeignClient("biometrics-bd-face")
@Service("helloClient")
public interface HelloClient {
    @RequestMapping(value = "/", method = GET)
    String hello();
}