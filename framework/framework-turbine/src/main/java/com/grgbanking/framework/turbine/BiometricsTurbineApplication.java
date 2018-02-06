package com.grgbanking.framework.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author wyf
 * http://localhost:8001/turbine.stream
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
public class BiometricsTurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiometricsTurbineApplication.class, args);
    }
}
