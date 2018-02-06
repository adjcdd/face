package com.grgbanking.framework.time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by wyf on 2017/6/6.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
// 扫描oauth2.0等其他依赖包的组件
@ComponentScan(basePackages = {"com.grgbanking.biometrics"})
//@ServletComponentScan(basePackages = {"com.jyall"})
public class BiometricsTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiometricsTimeApplication.class, args);
    }

}
