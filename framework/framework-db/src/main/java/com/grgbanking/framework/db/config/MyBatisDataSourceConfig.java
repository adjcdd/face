package com.grgbanking.framework.db.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author wyf
 * 数据源配置
 */
@Configuration
public class MyBatisDataSourceConfig implements EnvironmentAware{

	private RelaxedPropertyResolver propertyResolver;

	private static Logger logger = LoggerFactory.getLogger(MyBatisDataSourceConfig.class);

	private static String dbType;

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		dbType = propertyResolver.getProperty("dbType");
	}

	@Bean(name = "dataSource")
	@Primary
	public DataSource dataSource() {
		logger.debug("Configruing DataSource");
		HikariConfig datasource = new HikariConfig();
		datasource.setDriverClassName(propertyResolver.getProperty(dbType + ".driverClassName"));
		datasource.setJdbcUrl(propertyResolver.getProperty(dbType + ".url"));
		datasource.setUsername(propertyResolver.getProperty(dbType + ".username"));
		datasource.setPassword(propertyResolver.getProperty(dbType + ".password"));
		datasource.setMaximumPoolSize(50);
		datasource.setMinimumIdle(10);
		datasource.setConnectionTimeout(34000);
		datasource.setIdleTimeout(28740000);
		datasource.setMaxLifetime(28740000);
		datasource.setAutoCommit(false);
		return new HikariDataSource(datasource);
	}

//	@Bean(name = "logDataSource")
//	public DataSource logDataSource() {
//		logger.debug("Configruing DataSource");
//		HikariConfig datasource = new HikariConfig();
//		datasource.setDriverClassName(propertyResolver.getProperty("log.driverClassName"));
//		datasource.setJdbcUrl(propertyResolver.getProperty("log.url"));
//		datasource.setUsername(propertyResolver.getProperty("log.username"));
//		datasource.setPassword(propertyResolver.getProperty("log.password"));
//		datasource.setMaximumPoolSize(50);
//		datasource.setMinimumIdle(10);
//		datasource.setConnectionTimeout(34000);
//		datasource.setIdleTimeout(28740000);
//		datasource.setMaxLifetime(28740000);
//		datasource.setAutoCommit(true);
//		return new HikariDataSource(datasource);
//	}

	public static String getDbType() {
		return dbType;
	}
}
