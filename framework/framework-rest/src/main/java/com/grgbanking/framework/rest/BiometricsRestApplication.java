package com.grgbanking.framework.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wyf
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
// 扫描oauth2.0等其他依赖包的组件
@ComponentScan(basePackages = {"com.grgbanking.framework"})
// 加入认证，既作为认证服务器又作为资源服务器
//@EnableAuthorizationServer
//@EnableResourceServer
public class BiometricsRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiometricsRestApplication.class, args);
    }

}
