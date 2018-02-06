package com.grgbanking.framework.manager.initialization;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by wyf on 2017/6/8.
 */
@Configuration
public class BeanFactoryConfig implements EnvironmentAware, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(String name) {
        return (T) BeanFactoryConfig.applicationContext.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanFactoryConfig.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {

    }
}
