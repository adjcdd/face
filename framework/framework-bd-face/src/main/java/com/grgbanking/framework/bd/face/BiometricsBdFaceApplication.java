package com.grgbanking.framework.bd.face;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wyf
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableHystrixDashboard
@ComponentScan(basePackages = {"com.grgbanking.framework"})
public class BiometricsBdFaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiometricsBdFaceApplication.class, args);
    }
}
