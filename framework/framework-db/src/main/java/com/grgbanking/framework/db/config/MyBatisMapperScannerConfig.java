/**    
 * @Title: MyBatisMapperScannerConfig.java  
 * @Package com.jyall.service.centerhouse.common  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author zhao.liang    
 * @date 2016年3月12日 下午1:57:30  
 * @version V1.0    
 */
package com.grgbanking.framework.db.config;

import com.grgbanking.framework.db.mapper.BaseMapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wyf
 * 数据库映射文件配置
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter({MyBatisSessionFactoryConfig.class})
public class MyBatisMapperScannerConfig {
	private static Logger logger = LoggerFactory.getLogger(MyBatisMapperScannerConfig.class);
	
    @Bean(name="mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
    	logger.info("Database Scanner File");
        MapperScannerConfigurer readMapperScannerConfigurer = new MapperScannerConfigurer();
        readMapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        readMapperScannerConfigurer.setBasePackage(BaseMapper.class.getPackage().getName());
        readMapperScannerConfigurer.setBasePackage("com.grgbanking.framework");
        readMapperScannerConfigurer.setMarkerInterface(BaseMapper.class);
        return readMapperScannerConfigurer;
    }

//    @Bean(name="logMapperScannerConfigurer")
//    public MapperScannerConfigurer logMapperScannerConfigurer() {
//        logger.info("Database Scanner File");
//        MapperScannerConfigurer readMapperScannerConfigurer = new MapperScannerConfigurer();
//        readMapperScannerConfigurer.setSqlSessionFactoryBeanName("logSqlSessionFactory");
////        readMapperScannerConfigurer.setBasePackage(BaseMapper.class.getPackage().getName());
//        readMapperScannerConfigurer.setBasePackage("com.grgbanking.biometrics");
//        readMapperScannerConfigurer.setMarkerInterface(LogBaseMapper.class);
//        return readMapperScannerConfigurer;
//    }
    
}
