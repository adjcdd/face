package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.user.json.CookieUserJson;
import com.grgbanking.framework.domains.manager.user.param.*;
import com.grgbanking.framework.domains.manager.user.param.UserAddParam;
import com.grgbanking.framework.domains.manager.user.param.UserListParam;
import com.grgbanking.framework.domains.manager.user.param.UserLoginParam;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.UserService;
import com.grgbanking.framework.manager.session.CookieTool;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import com.grgbanking.framework.util.verify.VerifyCodeUtils;
import com.grgbanking.framework.domains.manager.user.param.UserUpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wyf on 2017/8/7.
 */
public class UserEService {

    private Logger logger = LoggerFactory.getLogger(UserEService.class);

    private UserService userService = BeanFactoryConfig.getBean("userService");

    @EService("ES-User-Login-T")
    public RestResponse login(UserLoginParam userLoginParam, HttpServletRequest request, HttpServletResponse response) {
        logger.info("开始执行登录服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
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
            logger.error("登录异常", e);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生系统异常");
        }
        return restResponse;
    }

    @EService("ES-User-AuthorityInit-T")
    @ManagerOperate("页面菜单初始化")
    public RestResponse authorityInit(HttpServletRequest request) {
        logger.info("开始执行菜单初始化服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.authorityInit(request);
        }catch (Exception e){
            logger.error("菜单初始化异常", e);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("发生菜单初始化异常");
        }
        return restResponse;
    }

    @EService("ES-User-VerifyCode-T")
    @ManagerOperate("获取验证码")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("开始执行获取验证码服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
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

    @EService("ES-User-CookieUsername-T")
    @ManagerOperate("获取Cookie用户名")
    public RestResponse getCookieUsername(HttpServletRequest request) throws Exception {
        logger.info("开始执行获取Cookie用户名服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            String username = CookieTool.getCookie(request, "username");
            if(!StringUtils.isEmpty(username)){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("查询成功");
                CookieUserJson userJson = new CookieUserJson();
                userJson.setUsername(username);
                restResponse.setResponseBody(userJson);
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

    @EService("ES-User-Add-T")
    @ManagerOperate("新增用户")
    public RestResponse addUser(UserAddParam userAddParam, HttpServletRequest request) throws Exception {
        logger.info("开始执行新增用户服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.addUser(userAddParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("新增用户发生异常");
        }
        return restResponse;
    }

    @EService("ES-User-Delete-T")
    @ManagerOperate("删除用户")
    public RestResponse deleteUser(UserPojo userPojo) throws Exception {
        logger.info("开始执行删除用户服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.deleteUser(userPojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("删除用户发生异常");
        }
        return restResponse;
    }

    @EService("ES-User-Session-T")
    @ManagerOperate("获取用户session")
    public RestResponse getSessionOfUser(HttpServletRequest request) throws Exception {
        logger.info("开始执行获取用户session服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("获取用户session服务成功");
           restResponse.setResponseBody(userPojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("获取用户session服务发生异常");
        }
        return restResponse;
    }

    @EService("ES-User-List-T")
    @ManagerOperate("查询用户列表")
    public RestResponse getUserList(UserListParam userListParam, HttpServletRequest request) throws Exception {
        logger.info("开始执行查询用户服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.getUserList(userListParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询用户列表发生异常");
        }
        return restResponse;
    }


    @EService("ES-User-Update-T")
    @ManagerOperate("更新用户")
    public RestResponse updateUser(UserUpdateParam userUpdateParam, HttpServletRequest request) throws Exception {
        logger.info("开始执行更新用户服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.updateUser(userUpdateParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("更新用户发生异常");
        }
        return restResponse;
    }


    @EService("ES-User-Resertpwd-T")
    @ManagerOperate("重置用户密码")
    public RestResponse resertPassword(UserUpdateParam userUpdateParam) throws Exception {
        logger.info("开始执行重置用户密码服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.resertPassword(userUpdateParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("重置用户密码发生异常");
        }
        return restResponse;
    }

    @EService("ES-User-Updatepwd-T")
    @ManagerOperate("修改用户密码")
    public RestResponse updatePassword(UserUpdateParam userUpdateParam,HttpServletRequest request) throws Exception {
        logger.info("开始执行修改用户密码服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.updatePassword(userUpdateParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("修改用户密码发生异常");
        }
        return restResponse;
    }

    @EService("ES-User-Authorizate-T")
    @ManagerOperate("用户授权")
    public RestResponse userAuthorizate(UserPojo userPojo) throws Exception {
        logger.info("开始执行用户绑定角色服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.userAuthorizate(userPojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("用户授权发生异常");
        }
        return restResponse;
    }

    @EService("ES-User-Info-T")
    @ManagerOperate("查询用户详情")
    public RestResponse getUserInfo(UserPojo userPojo) throws Exception {
        logger.info("开始执行查询用户服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.getUserInfo(userPojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询用户发生异常");
        }
        return restResponse;
    }


    @EService("ES-User-RoleList-T")
    @ManagerOperate("查询用户角色列表")
    public RestResponse getUserRoleList(UserPojo userPojo,HttpServletRequest request) throws Exception {
        logger.info("开始执行查询用户角色列表服务，请求标识为 : " + RequestIdentifierLocalHolder.getRequestIdentifier());
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.userService.getUserRoleList(userPojo,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询用户角色列表发生异常");
        }
        return restResponse;
    }

}
