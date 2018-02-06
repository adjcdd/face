package com.grgbanking.framework.dashboard.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

/**
 * @author wyf
 * 
 *         通用配置类
 */
@Configuration
public class WebConfig implements EnvironmentAware {

	// 解析application.yml
	private RelaxedPropertyResolver propResolver;
	/**
	 * 解决中文内容编码问题，统一用UTF-8编码
	 * 
	 * @return
	 */
	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return characterEncodingFilter;
	}

	@Override
	public void setEnvironment(Environment environment) {
		propResolver = new RelaxedPropertyResolver(environment);
	}

}
