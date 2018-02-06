/**
 * @Copyright (c) 2017, 广电运通信息科技有限公司 All Rights Reserved.
 */
package com.grgbanking.framework.util.http;

import com.squareup.okhttp.*;
import okio.BufferedSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description TODO
 * @Author wyf
 * @Date 2017年5月31日 下午13:44
 */
public final class OkHttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

	private static final OkHttpClient client = new OkHttpClient();
	private static final MediaType MEDIA_TYPE_JSON = MediaType
			.parse("application/json; charset=utf-8");
	private static Logger LOGGER = LoggerFactory.getLogger(OkHttpUtils.class);

	/**
	 * POST提交Json数据
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, final String json, Map<String, String> headers) throws Exception {
		RequestBody body = new RequestBody() {
			@Override
			public MediaType contentType() {
				return MEDIA_TYPE_JSON;
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.writeUtf8(json);
			}

			@Override
			public long contentLength() throws IOException {
				return json.getBytes().length;
			}
		};
		client.setSslSocketFactory(TrustAllHostnameVerifier.createSSLSocketFactory());
		client.setHostnameVerifier(new TrustAllHostnameVerifier());
		Request.Builder builder = new Request.Builder().url(url);
		if(null != headers && headers.size() > 0){
			Set<String> headerKeys = headers.keySet();
			for(Iterator<String> ite = headerKeys.iterator(); ite.hasNext();){
				String key = ite.next();
				String value = headers.get(key);
				builder.addHeader(key, value);
			}
		}

		Request request = builder.post(body).build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("服务器端连接断开", e);
		}
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("服务器端错误:" + response);
		}
	}

	/**
	 * POST提交键值对
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, Object> params)
			throws Exception {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(null != key && null != value){
				String clazzName = value.getClass().getName();
				if(clazzName.equals("java.lang.String")){
					builder.add(key, String.valueOf(value));
				}else{
					builder.add(key, "" + value);
				}
			}
		}
		RequestBody body = builder.build();

		Request request = new Request.Builder().url(url).post(body).build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (Exception e) {
			LOGGER.error("POST请求服务器端连接断开", e);
			throw new Exception("POST请求服务器端连接断开", e);
		}
		if (response.isSuccessful()) {
			String result = response.body().string();
			LOGGER.info("POST请求服务器端返回结果:{}", result);
			return result;
		} else {
			LOGGER.error("POST请求服务器端错误:{}", response);
			throw new IOException("POST请求服务器端错误:" + response);
		}
	}

	/**
	 * 发生GET请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params) throws Exception{
		StringBuilder sb = new StringBuilder("");
		if(null != params && params.size() > 0){
			Set<String> keys = params.keySet();
			for(Iterator<String> ite = keys.iterator(); ite.hasNext();){
				String key = ite.next();
				sb.append("&" + key + "=" + params.get(key));
			}
		}
		if(!"".equals(sb.toString())){
			url += "?" + sb.toString().substring(1,sb.toString().length());
		}
		Request request = new Request.Builder().url(url).get().build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (Exception e) {
			LOGGER.error("GET请求服务器端连接断开", e);
			throw new Exception("GET请求服务器端连接断开", e);
		}
		if (response.isSuccessful()) {
			String result = response.body().string();
			LOGGER.info("GET请求服务器端返回结果:{}", result);
			return result;
		} else {
			LOGGER.error("GET请求服务器端错误:{}", response);
			throw new IOException("GET请求服务器端错误:" + response);
		}
	}

}
