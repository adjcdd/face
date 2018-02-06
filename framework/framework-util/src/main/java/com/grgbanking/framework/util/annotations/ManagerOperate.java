package com.grgbanking.framework.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wyf on 2017/9/19.
 * 凡是被此注解标识的企业级服务都将被记入日志
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerOperate {

    String value() default "";

}
