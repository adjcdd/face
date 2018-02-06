package com.grgbanking.framework.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author wyf
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
// 扫描oauth2.0等其他依赖包的组件
@ComponentScan(basePackages = {"com.grgbanking.biometrics"})
@ImportResource({ "/integration-context.xml", "/cxf-servlet.xml" })
public class BiometricsWebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiometricsWebserviceApplication.class, args);
    }

}
