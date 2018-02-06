package com.grgbanking.framework.manager.eservice;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.authority.param.AuthorityAddParam;
import com.grgbanking.framework.domains.manager.authority.param.AuthorityListParam;
import com.grgbanking.framework.domains.manager.authority.param.AuthorityUpdateParam;
import com.grgbanking.framework.domains.manager.authority.pojo.AuthorityPojo;
import com.grgbanking.framework.manager.initialization.BeanFactoryConfig;
import com.grgbanking.framework.manager.service.AuthorityService;
import com.grgbanking.framework.util.annotations.EService;
import com.grgbanking.framework.util.annotations.ManagerOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class AuthorityEService {

    private Logger logger = LoggerFactory.getLogger(AuthorityEService.class);

    private AuthorityService authorityService = BeanFactoryConfig.getBean("authorityService");

    @EService("ES-Authority-List-T")
    @ManagerOperate("查询菜单列表")
    public RestResponse getAuthorityList(AuthorityListParam authorityListParam) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.getAuthorityList(authorityListParam);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询菜单列表出现异常");
        }
        return restResponse;
    }

    @EService("ES-Authority-Add-T")
    @ManagerOperate("新增菜单")
    public RestResponse addAuthority(AuthorityAddParam authorityAddParam,HttpServletRequest request){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.addAuthority(authorityAddParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("新增菜单出现异常");
        }
        return restResponse;
    }

    @EService("ES-Authority-Update-T")
    @ManagerOperate("更新菜单")
    public RestResponse updateAuthority(AuthorityUpdateParam authorityUpdateParam, HttpServletRequest request){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.updateAuthority(authorityUpdateParam,request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("更新菜单出现异常");
        }
        return restResponse;
    }

    @EService("ES-Authority-Delete-T")
    @ManagerOperate("删除菜单")
    public RestResponse deleteAuthority(AuthorityPojo authorityPojo){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.deleteAuthority(authorityPojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("删除菜单出现异常");
        }
        return restResponse;
    }

    @EService("ES-Authority-All-T")
    @ManagerOperate("查询全部菜单")
    public RestResponse getAllAuthority(HttpServletRequest request){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.getAllAuthority(request);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询所有菜单出现异常");
        }
        return restResponse;
    }

    @EService("ES-Authority-AllPid-T")
    @ManagerOperate("查询全部父级菜单")
    public RestResponse getAllAuthorityByPid(){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.getAllAuthorityByPid();
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询父级菜单出现异常");
        }
        return restResponse;
    }

    @EService("ES-Authority-Info-T")
    @ManagerOperate("查看菜单详情")
    public RestResponse getAuthorityInfo(AuthorityPojo authorityPojo){
        RestResponse restResponse = new RestResponse();
        try {
            restResponse = this.authorityService.getAuthorityInfo(authorityPojo);
        }catch (Exception e){
            restResponse.getResponseHeader().setErrorCode(ErrorCode.EXCEPTION);
            restResponse.getResponseHeader().setMessage("查询菜单详情出现异常");
        }
        return restResponse;
    }

}
