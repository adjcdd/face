package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.role.param.RoleAddParam;
import com.grgbanking.framework.domains.manager.role.param.RoleDeleteParam;
import com.grgbanking.framework.domains.manager.role.param.RoleListParam;
import com.grgbanking.framework.domains.manager.role.param.RoleUpdateParam;
import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.RoleService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class RoleEService {

    private Logger logger = LoggerFactory.getLogger(RoleEService.class);

    private RoleService roleService = BeanFactoryConfig.getBean("roleService");

    @EService("ES-Role-List-T")
    @ManagerOperate("查询角色列表")
    public RestResponse getRoleList(RoleListParam roleListParam, HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.getRoleList(roleListParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询角色列表出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-Add-T")
    @ManagerOperate("新增角色")
    public RestResponse addRole(RoleAddParam roleAddParam, HttpServletRequest request){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.addRole(roleAddParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("新增角色出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-Info-T")
    @ManagerOperate("查询角色详情")
    public RestResponse getRoleInfo(RolePojo rolePojo,HttpServletRequest request){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.getRoleInfo(rolePojo,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询角色详情出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-ForEdit-T")
    @ManagerOperate("查询角色详情")
    public RestResponse getRoleForEdit(RolePojo rolePojo){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.getRoleForEdit(rolePojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询角色详情出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-Update-T")
    @ManagerOperate("更新角色")
    public RestResponse updateRole(RoleUpdateParam roleUpdateParam, HttpServletRequest request){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.updateRole(roleUpdateParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("更新角色出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-Delete-T")
    @ManagerOperate("删除角色")
    public RestResponse deleteRole(RoleDeleteParam roleDeleteParam){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.deleteRole(roleDeleteParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("删除角色出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-All-T")
    @ManagerOperate("查询全部角色")
    public RestResponse getAllRole(RolePojo rolePojo){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.getAllRole(rolePojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询所有角色出现异常");
        }
        return restResponse;
    }

    @EService("ES-Role-Authorite-T")
    @ManagerOperate("角色授权")
    public RestResponse roleAuthorite(RolePojo rolePojo){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.roleService.roleAuthorite(rolePojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("角色授权出现异常");
        }
        return restResponse;
    }

}
