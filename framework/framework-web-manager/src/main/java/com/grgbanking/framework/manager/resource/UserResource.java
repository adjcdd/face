package com.grgbanking.framework.manager.resource;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.user.param.UserLoginParam;
import com.grgbanking.framework.manager.service.UserService;
import com.grgbanking.framework.manager.session.CookieTool;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import com.grgbanking.framework.util.verify.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 49188 on 2017/7/31.
 */
@Controller
@RequestMapping("/user")
public class UserResource {

    private Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        HttpSessionTool.saveSession(request, "verifyCode", verifyCode);

        //生成图片  
        int w = 90, h = 35;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse login(UserLoginParam userLoginParam, HttpServletRequest request, HttpServletResponse response) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.login(userLoginParam, request);
            // 验证成功且设置记住用户名的情况下保存用户名到Cookie
            if(ErrorCode.SUCCESS.equals(restResponse.getResponseHeader().getErrorCode()) && userLoginParam.isRememberMe()){
                CookieTool.setCookie(request, response, "username", userLoginParam.getUsername());
            }else if(ErrorCode.SUCCESS.equals(restResponse.getResponseHeader().getErrorCode()) && !userLoginParam.isRememberMe()){
                CookieTool.clearCookie(request,response,"username");
            }
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }


    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse loginOut(HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.loginOut(request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }

    @RequestMapping(value = "/getCookieUsername", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getCookieUsername(HttpServletRequest request) throws Exception {
        RestResponse restResponse = new RestResponse();
        try {
            String username = CookieTool.getCookie(request, "username");
            if(!StringUtils.isEmpty(username)){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("查询成功");
                restResponse.setResponseBody(username);
            }else{
                restResponse.getResponseHeader().setErrorCode(ErrorCode.EMPTY_RESULT);
                restResponse.getResponseHeader().setMessage("当前客户端未保存用户");
            }
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询Cookie中的用户名发生异常");
        }
        return restResponse;
    }



}
