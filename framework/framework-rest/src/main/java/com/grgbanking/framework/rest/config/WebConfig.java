package com.grgbanking.framework.rest.config;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import java.io.*;

/**
 * @author wyf
 * 
 *         通用配置类
 */
@Configuration
@PropertySource("classpath:/tomcat.https.properties")
@EnableConfigurationProperties(WebConfig.TomcatSslConnectorProperties.class)
public class WebConfig implements EnvironmentAware {

	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

	// 解析application.yml
	private RelaxedPropertyResolver propResolver;

	private static String keystorePath = System.getProperty("user.home") + File.separator + "KeyStore" + File.separator + "REST" + File.separator;

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

		File file = new File(keystorePath);
		if(file.exists()){
			try {
				logger.info("开始删除KeyStore原有目录...");
				deleteDir(file);
			}catch (Exception e){
				logger.error("删除目录" + keystorePath + "异常", e);
			}
		}
		File newFile = new File(keystorePath);
		newFile.setExecutable(true, false);
		newFile.setWritable(true,false);
		newFile.setReadable(true, false);
		if(!newFile.exists()){
			newFile.mkdirs();
		}
		Resource[] resources = null;
		try {
			resources = new PathMatchingResourcePatternResolver().getResources("classpath:.keystore");
		}catch (IOException e){
			logger.error("获取keystore资源异常!", e);
		}
		File keyStoreFile = null;
		if(null != resources && resources.length > 0){
			keyStoreFile = copyKeystore2KeystorePath(resources[0]);
		}
		if(null != keyStoreFile){
			new TomcatSslConnectorProperties().setKeystoreFile(keyStoreFile);
		}
	}

	private static File copyKeystore2KeystorePath(Resource resource){
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			// 将DLL拷贝到@dllPath目录下
			inputStream = resource.getInputStream();
			String targetFileName = resource.getFilename();

			String fullPath = keystorePath + File.separator + targetFileName;
			logger.info("KeyStore拷贝目标地址是: " + fullPath);
			File targetFile = new File(fullPath);
			if(!targetFile.exists()){
				targetFile.createNewFile();
			}

			outputStream = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			int length;
			while ( (length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
			return targetFile;
		}catch (FileNotFoundException e){
			logger.info("Occur A Common Error");
		}catch (IOException e) {
			logger.error("拷贝中科奥森人脸识别DLL异常!", e);
		}finally {
			if(null != inputStream){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static void deleteDir(File file){
		if(file.isDirectory()){
			File[] childFiles = file.listFiles();
			for(File chileFile : childFiles){
				deleteDir(chileFile);
			}
		}
        else{
            file.delete();
            logger.info("KeyStore目录删除成功");
        }
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer(TomcatSslConnectorProperties properties) {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
		return tomcat;
	}

	private Connector createSslConnector(TomcatSslConnectorProperties properties) {
		Connector connector = new Connector();
		properties.configureConnector(connector);
		return connector;
	}

	@ConfigurationProperties(prefix = "custom.tomcat.https")
	public static class TomcatSslConnectorProperties {
		private static Integer port;
		private static Boolean ssl = true;
		private static Boolean secure = true;
		private static String scheme = "https";
		private static File keystore;
		private static String keystorePassword;

		public void configureConnector(Connector connector) {
			if (port != null) {
				connector.setPort(port);
			}
			if (secure != null) {
				connector.setSecure(secure);
			}
			if (scheme != null) {
				connector.setScheme(scheme);
			}
			if (ssl != null) {
				connector.setProperty("SSLEnabled", ssl.toString());
			}
			if (keystore != null && keystore.exists()) {
				connector.setProperty("keystoreFile", keystore.getAbsolutePath());
				connector.setProperty("keyStorePassword", keystorePassword);
			}
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public Boolean getSsl() {
			return ssl;
		}

		public void setSsl(Boolean ssl) {
			this.ssl = ssl;
		}

		public Boolean getSecure() {
			return secure;
		}

		public void setSecure(Boolean secure) {
			this.secure = secure;
		}

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public File getKeystore() {
			return keystore;
		}

		public void setKeystoreFile(File keystore) {
			this.keystore = keystore;
		}

		public String getKeystorePassword() {
			return keystorePassword;
		}

		public void setKeystorePassword(String keystorePassword) {
			this.keystorePassword = keystorePassword;
		}
	}

}
