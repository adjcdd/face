package com.grgbanking.framework.manager.interceptor;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wjian17 on 2017/9/25.
 */
public class LoginInterceptor implements HandlerInterceptor{

	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		logger.info("开始请求拦截，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
		UserPojo userPojo = HttpSessionTool.getValue(httpServletRequest, "user");
//		String url=httpServletRequest.getRequestURI();
		String path=httpServletRequest.getContextPath();
		if (userPojo == null) {
//			if((path+"/monitoring").equals(url)){
//				return true;
//			}
			logger.info("跳转到login页面！");
			//String path = httpServletRequest.getContextPath();
			String type = httpServletRequest.getHeader("X-Requested-With");
			if(StringUtils.equals("XMLHttpRequest", type)) {
				String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + path + "/";
				httpServletResponse.setHeader("SESSIONSTATUS", ErrorCode.NO_EFFACTIVE);
			}else{
				httpServletResponse.sendRedirect(path+"/login");
			}
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
