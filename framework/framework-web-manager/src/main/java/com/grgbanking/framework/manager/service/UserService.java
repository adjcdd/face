package com.grgbanking.framework.manager.service;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.authority.pojo.AuthorityPojo;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;
import com.grgbanking.framework.domains.manager.user.json.UserRoleListJson;
import com.grgbanking.framework.domains.manager.user.param.UserAddParam;
import com.grgbanking.framework.domains.manager.user.param.UserListParam;
import com.grgbanking.framework.domains.manager.user.param.UserLoginParam;
import com.grgbanking.framework.domains.manager.user.param.UserUpdateParam;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.mapper.AuthorityMapper;
import com.grgbanking.framework.manager.mapper.RoleMapper;
import com.grgbanking.framework.manager.mapper.UserMapper;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import com.grgbanking.framework.util.md5.MD5Util;
import com.grgbanking.framework.util.page.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wyf on 2017/8/1.
 */
@Service("userService")
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @ManagerOperate("用户登录")
    public RestResponse login(UserLoginParam userLoginParam, HttpServletRequest request) throws Exception{
        RestResponse restResponse = new RestResponse();
        try {
            String verifyCodeSession = HttpSessionTool.getValue(request, "verifyCode");
            String verifyCodeRequest = userLoginParam.getVerifyCode();
            if(StringUtils.isEmpty(verifyCodeSession) || StringUtils.isEmpty(verifyCodeRequest) || !verifyCodeSession.toLowerCase().equals(verifyCodeRequest.toLowerCase())){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.LOGIN_FAIL);
                restResponse.getResponseHeader().setMessage("验证码错误");
                return restResponse;
            }
            String password = MD5Util.MD5(userLoginParam.getPassword());
            UserPojo userPojo = this.userMapper.getUserByUsername(userLoginParam);
            // 登录成功
            if(null != userPojo){
                if(userPojo.getStatus() == 0 && !"admin".equals(userPojo.getUsername())){
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.USER_FORBIDEN);
                    restResponse.getResponseHeader().setMessage("此用户已被禁用");
                    return restResponse;
                }
                if(userPojo.getPassword().equals(password)){
                    HttpSessionTool.saveSession(request, "user", userPojo);
                /*String authoritys = this.rebuildAuthoritys(userPojo);
                restResponse.setResponseBody(authoritys);*/
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                    restResponse.getResponseHeader().setMessage("登录成功");
                }else{
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.LOGIN_FAIL);
                    restResponse.getResponseHeader().setMessage("密码输入错误");
                }
            }else{
                restResponse.getResponseHeader().setErrorCode(ErrorCode.LOGIN_FAIL);
                restResponse.getResponseHeader().setMessage("此用户不存在");
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "登录系统出现异常", e);
            e.printStackTrace();
        }
        return restResponse;
    }

    @ManagerOperate("用户退出")
    public RestResponse loginOut(HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始退出系统!");
        RestResponse restResponse = new RestResponse();
        try {
            //清空session中user
            HttpSessionTool.clearValue(request,"user");
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("退出成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "退出系统出现异常", e);
            throw e;
        }
        return restResponse;
    }

    public RestResponse authorityInit(HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始初始化菜单!");
        RestResponse restResponse = new RestResponse();
        try {
            //获取页面session
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            if(userPojo!=null){
                String authoritys = "";
                if(userPojo.getUsername().equals("admin")){
                    List<AuthorityPojo> rolePojoList = this.authorityMapper.getAllAuthorityByAdmin();
                    authoritys = "[" + this.generatorAuthorityJson(rolePojoList,0l) + "]";
                }else {
                    userPojo = this.userMapper.getAuthorityByUsername(userPojo);
                    authoritys = this.rebuildAuthoritys(userPojo);
                }
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("菜单初始化成功");
                restResponse.setResponseBody(authoritys);
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "初始化菜单出现异常", e);
            throw e;
        }
        return restResponse;
    }


    @Transactional(rollbackFor = Exception.class)
    public RestResponse addUser(UserAddParam userAddParam,HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增用户!");
        RestResponse restResponse = new RestResponse();
        try {
            UserPojo userPojo = new UserPojo();
            BeanUtils.copyProperties(userAddParam,userPojo);
            Integer flag = this.userMapper.checkIsExistUser(userPojo);
            if(userPojo.getStatus()==null){
                userPojo.setStatus(1);
            }
            if(flag!=0&&userPojo.getStatus()==1){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.DUPLICATE_DATA);
                restResponse.getResponseHeader().setMessage("用户已存在");
            }else {
                UserPojo userPojo1 = HttpSessionTool.getValue(request,"user");
                userPojo.setCreateUser(userPojo1==null?"System":userPojo1.getUsername());
//                String password = userPojo.getPassword();
//                userPojo.setPassword(MD5Util.MD5(password));
                Date now = new Date();
                userPojo.setCreateTime(now);
                this.userMapper.addUser(userPojo);
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("新增用户成功");
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增用户出现异常", e);
            throw e;
        }
        return restResponse;
    }


    @Transactional(rollbackFor = Exception.class)
    public RestResponse deleteUser(UserPojo userPojo) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始删除用户!");
        RestResponse restResponse = new RestResponse();
        try {
            this.userMapper.clearUserRole(userPojo.getId());
            this.userMapper.deleteUser(userPojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("删除用户成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "删除用户出现异常", e);
            throw e;
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse updateUser(UserUpdateParam userUpdateParam,HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新用户!");
        RestResponse restResponse = new RestResponse();
        try {
            UserPojo userPojo = new UserPojo();
            BeanUtils.copyProperties(userUpdateParam,userPojo);
            Integer flag = this.userMapper.checkIsExistUser(userPojo);
            if(flag!=0&&userUpdateParam.getStatus()==1){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.DUPLICATE_DATA);
                restResponse.getResponseHeader().setMessage("用户已存在");
            }else {
                UserPojo userPojo1 = HttpSessionTool.getValue(request,"user");
                userPojo.setUpdateUser(userPojo1.getUsername());
//                String password = userPojo.getPassword();
//                userPojo.setPassword(MD5Util.MD5(password));
                Date now = new Date();
                userPojo.setUpdateTime(now);
                this.userMapper.editUser(userPojo);
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("更新用户成功");
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新用户出现异常", e);
            throw e;
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse resertPassword(UserUpdateParam userUpdateParam) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始重置用户密码!");
        RestResponse restResponse = new RestResponse();
        try {
            this.userMapper.resertPassword(userUpdateParam.getId());
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("重置密码成功,初始密码为123456");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "重置用户密码出现异常", e);
            throw e;
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse updatePassword(UserUpdateParam userUpdateParam,HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始修改用户密码!");
        RestResponse restResponse = new RestResponse();
        try {
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            UserLoginParam userLoginParam = new UserLoginParam();
            userLoginParam.setUsername(userPojo.getUsername());
            userPojo = this.userMapper.getUserByUsername(userLoginParam);
            String oriPasswrod = userUpdateParam.getOriPassword();
            String password = userUpdateParam.getPassword();
            String rePassword = userUpdateParam.getRePassword();
            if (userPojo.getPassword().equals(MD5Util.MD5(oriPasswrod))) {
                if(password.equals(rePassword)){
                    if (oriPasswrod.equals(password)) {
                        restResponse.getResponseHeader().setErrorCode(ErrorCode.NO_IDENTICAL);
                        restResponse.getResponseHeader().setMessage("新密码与原密码不能相同");
                    } else {
                        this.userMapper.updatePassword(userPojo.getId(), MD5Util.MD5(password));
                        restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                        restResponse.getResponseHeader().setMessage("修改用户密码成功");
                    }
                }else{
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.NO_ADAPT);
                    restResponse.getResponseHeader().setMessage("新密码和确认密码不一致");
                }
            } else {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.NO_ORI_PWD);
                restResponse.getResponseHeader().setMessage("原密码输入错误");
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "修改用户密码出现异常", e);
            throw e;
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse userAuthorizate(UserPojo userPojo) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始用户绑定角色!");
        RestResponse restResponse = new RestResponse();
        try {
                List<RolePojo> rolePojoList = userPojo.getRolePojoList();
                Long userId = userPojo.getId();
                //清空用户角色
                this.userMapper.clearUserRole(userId);
                //重新绑定用户角色
                if(rolePojoList!=null) {
                    for (RolePojo rolePojo : rolePojoList) {
                        this.userMapper.bindUserRole(userId, rolePojo.getId());
                    }
                }
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("用户授权成功");

        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "用户授权出现异常", e);
            throw e;
        }
        return restResponse;
    }

    public RestResponse getUserList(UserListParam userListParam,HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询用户列表!");
        RestResponse restResponse = new RestResponse();
        try {
            userListParam.setCurrentCount((userListParam.getPageNo() - 1) * userListParam.getPageSize());
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            List<UserPojo> userPojoList = new ArrayList<UserPojo>();
            long totalCount = 0;
            if("admin".equals(userPojo.getUsername())){
                userPojoList = this.userMapper.getUserListByAdmin(userListParam);
                totalCount = this.userMapper.getUserCountByAdmin(userListParam);
            }else{
                userPojoList = this.userMapper.getUserList(userListParam);
                totalCount = this.userMapper.getUserCount(userListParam);
            }

            PaginationJson page = new PaginationJson();
            page.setPageNo(userListParam.getPageNo());
            page.setPageSize(userListParam.getPageSize());
            page.setData(userPojoList);
            page.setTotalCount(totalCount);
            page.setTotalPage(PageUtil.calTotalPage(totalCount, userListParam.getPageSize()));
            restResponse.setResponseBody(page);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询用户列表出现异常", e);
            throw e;
        }
        return restResponse;
    }

    public RestResponse getUserInfo(UserPojo userPojo) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询用户详情!");
        RestResponse restResponse = new RestResponse();
        try {
            userPojo = this.userMapper.getUserDetial(userPojo);
            restResponse.setResponseBody(userPojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询用户详情出现异常", e);
            throw e;
        }
        return restResponse;
    }


    public RestResponse getUserRoleList(UserPojo userPojo,HttpServletRequest request) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询用户角色列表!");
        RestResponse restResponse = new RestResponse();
        try {
            userPojo = this.userMapper.getUserInfo(userPojo);
            RolePojo rolePojo = new RolePojo();
            UserPojo userPojo1 = HttpSessionTool.getValue(request,"user");
            long totalCount = 0;
            List<RolePojo> rolePojoList = new ArrayList<RolePojo>();
            List<RolePojo> rolePojoSlelectList = userPojo.getRolePojoList();
            if("admin".equals(userPojo1.getUsername())){
                rolePojoList = this.roleMapper.getAllRoleByAdmin(rolePojo);//所有匹配名称的角色
            }else{
                rolePojoList = this.roleMapper.getAllRole(rolePojo);//所有匹配名称的角色
            }
            UserRoleListJson userRoleListJson = new UserRoleListJson();
            rolePojoList.removeAll(rolePojoSlelectList);
            userRoleListJson.setRolePojoReleaseList(rolePojoList);
            userRoleListJson.setRolePojoSelectList(rolePojoSlelectList);
            restResponse.setResponseBody(userRoleListJson);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询用户角色列表出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 重构权限格式，用于传回与前端匹配格式
     * @param userPojo
     * @return
     */
    public String rebuildAuthoritys(UserPojo userPojo){
        String authoritysJson = "";
        List<RolePojo> rolePojoList = userPojo.getRolePojoList();
        Set<AuthorityPojo> authorityPojoSet = new TreeSet<>();
        for(RolePojo rolePojo:rolePojoList){
            authorityPojoSet.addAll(rolePojo.getAuthorityPojoList());
        }
        //循环遍历构成权限json
        authoritysJson = "[" + this.generatorAuthorityJson(new ArrayList<AuthorityPojo>(authorityPojoSet),0l) + "]";
        return authoritysJson;
    }

    public String generatorAuthorityJson(List<AuthorityPojo> authorityPojoList,Long pid){
        String authoritysJson="";
        for (AuthorityPojo authorityPojo : authorityPojoList) {
            if (authorityPojo.getPid() == pid) {
                authoritysJson += "{";
                authoritysJson += authorityPojo.parseString();
                authoritysJson += this.generatorAuthorityJson(authorityPojoList, authorityPojo.getId());
                authoritysJson += "},";
            }
        }
        if(authoritysJson.endsWith(",")){
            authoritysJson=authoritysJson.substring(0,authoritysJson.length()-1);
        }
        if(pid!=0&&!"".equals(authoritysJson)){
            authoritysJson=",'sub':["+authoritysJson+"]";
        }
        return authoritysJson;
    }

}
