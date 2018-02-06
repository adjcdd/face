package com.grgbanking.framework.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wyf
 * http://localhost:7979/hystrix
 * single: http://localhost:8080/grgbanking/biometrics/hystrix.stream
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@ComponentScan(basePackages = {"com.grgbanking.framework"})
public class BiometricsDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiometricsDashboardApplication.class, args);
    }
}
