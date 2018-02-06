package com.grgbanking.framework.metrics.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * Created by wyf on 2017/7/4.
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class SpringBootAdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminWebApplication.class, args);
    }
}
