package com.grgbanking.framework.manager.service;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.authority.param.AuthorityAddParam;
import com.grgbanking.framework.domains.manager.authority.param.AuthorityListParam;
import com.grgbanking.framework.domains.manager.authority.param.AuthorityUpdateParam;
import com.grgbanking.framework.domains.manager.authority.pojo.AuthorityPojo;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.mapper.AuthorityMapper;
import com.grgbanking.framework.manager.mapper.UserMapper;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import com.grgbanking.framework.util.page.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wyf on 2017/8/26.
 */
@Service("authorityService")
public class AuthorityService {

    private Logger logger = LoggerFactory.getLogger(AuthorityService.class);

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询菜单列表
     *
     * @param authorityListParam
     * @return
     * @throws Exception
     */
    public RestResponse getAuthorityList(AuthorityListParam authorityListParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询菜单列表!");
        RestResponse restResponse = new RestResponse();
        try {
            authorityListParam.setCurrentCount((authorityListParam.getPageNo() - 1) * authorityListParam.getPageSize());
            List<AuthorityPojo> pauthorityList = this.authorityMapper.getAllAuthorityByPid();
            List<AuthorityPojo> authorityList = this.authorityMapper.getAuthorityList(authorityListParam,pauthorityList);
            PaginationJson page = new PaginationJson();
            page.setPageNo(authorityListParam.getPageNo());
            page.setPageSize(authorityListParam.getPageSize());
            page.setData(authorityList);
            long totalCount = this.authorityMapper.getAuthorityCount(authorityListParam);
            page.setTotalCount(totalCount);
            page.setTotalPage(PageUtil.calTotalPage(totalCount, authorityListParam.getPageSize()));
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(page);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询菜单列表出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 查询所有菜单
     * @return
     * @throws Exception
     */
    public RestResponse getAllAuthority(HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询所有菜单!");
        RestResponse restResponse = new RestResponse();
        try {
            List<AuthorityPojo> authorityPojoList = new ArrayList<>();
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            if(!"admin".equals(userPojo.getUsername())){
                userPojo = this.userMapper.getAuthorityByUsername(userPojo);
                List<RolePojo> rolePojoList = userPojo.getRolePojoList();
                Set<AuthorityPojo> authorityPojoSet = new TreeSet<>();
                for(RolePojo rolePojo:rolePojoList){
                    authorityPojoSet.addAll(rolePojo.getAuthorityPojoList());
                }
                authorityPojoList = new ArrayList<>(authorityPojoSet);
                List<AuthorityPojo> authorityPojos = new ArrayList<AuthorityPojo>();//admin独有的权限
                for (AuthorityPojo authorityPojo:authorityPojoList) {
                    if(authorityPojo.getAuthorityLevel()==0){
                        authorityPojos.add(authorityPojo);
                    }
                }
                authorityPojoList.removeAll(authorityPojos);
            }else{
                authorityPojoList = this.authorityMapper.getAllAuthority();
            }
            String authoritysJson = "[" + this.generatorAuthorityJson(authorityPojoList,0l) + "]";
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(authoritysJson);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询所有菜单出现异常", e);
            throw e;
        }
        return restResponse;
    }

    public String generatorAuthorityJson(List<AuthorityPojo> authorityPojoList,Long pid){
        String authoritysJson="";
        for (AuthorityPojo authorityPojo : authorityPojoList) {
            if (authorityPojo.getPid() == pid) {
                authoritysJson += "{";
                authoritysJson += "'id':'"+authorityPojo.getId()+"','authorityName':'"+authorityPojo.getAuthorityName()+"'";
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

    /**
     * 查询所有父级菜单
     * @return
     * @throws Exception
     */
    public RestResponse getAllAuthorityByPid() throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询所有父级菜单!");
        RestResponse restResponse = new RestResponse();
        try {
            List<AuthorityPojo> authorityPojoList = this.authorityMapper.getAllAuthorityByPid();
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(authorityPojoList);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询父级菜单出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 查询菜单详情
     * @return
     * @throws Exception
     */
    public RestResponse getAuthorityInfo(AuthorityPojo authorityPojo) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询菜单详情!");
        RestResponse restResponse = new RestResponse();
        try {
            authorityPojo = this.authorityMapper.getAuthorityInfo(authorityPojo);
            if(authorityPojo.getPid()==0){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.NO_EDIT_DELETE);
                restResponse.getResponseHeader().setMessage("一级菜单不可编辑");
                return restResponse;
            }
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(authorityPojo);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询菜单详情出现异常", e);
            throw e;
        }
        return restResponse;
    }


    /**
     * 新增菜单
     *
     * @param authorityAddParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse addAuthority(AuthorityAddParam authorityAddParam,HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增菜单!");
        RestResponse restResponse = new RestResponse();
        try {
            if(authorityAddParam.getClassName()!=null&&authorityAddParam.getImg()==null||"".equals(authorityAddParam.getImg())){
                authorityAddParam.setImg("resources/common/image/"+authorityAddParam.getClassName()+"1.png");
            }
            AuthorityPojo authorityPojo = new AuthorityPojo();
            BeanUtils.copyProperties(authorityAddParam,authorityPojo);
            //有效菜单是否存在
            Integer flag = this.authorityMapper.checkIsExistAuthority(authorityPojo);
            if(flag!=0){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.DUPLICATE_DATA);
                restResponse.getResponseHeader().setMessage("菜单已存在");
            }
            Date now  = new Date();
            authorityPojo.setCreateTime(now);
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            authorityPojo.setCreateUser(userPojo.getUsername());
            if(authorityPojo.getStatus()==null) {
                authorityPojo.setStatus(0);
            }
            Long sortId = this.authorityMapper.selectMaxSortId(authorityPojo.getPid());
            authorityPojo.setSortId(sortId+1);
            this.authorityMapper.addAuthority(authorityPojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("新增菜单成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增菜单出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 更新菜单
     *
     * @param authorityUpdateParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse updateAuthority(AuthorityUpdateParam authorityUpdateParam,HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新菜单!");
        RestResponse restResponse = new RestResponse();
        try {
            AuthorityPojo authorityPojo = new AuthorityPojo();
            BeanUtils.copyProperties(authorityUpdateParam, authorityPojo);
            if(authorityUpdateParam.getClassName()!=null&&authorityUpdateParam.getImg()==null||"".equals(authorityUpdateParam.getImg())){
                authorityUpdateParam.setImg("resources/common/image/"+authorityUpdateParam.getClassName()+"1.png");
            }
            Integer flag = this.authorityMapper.checkIsExistAuthority(authorityPojo);
            if (flag!=0) {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.CALL_CLIENT_EXISTS);
                restResponse.getResponseHeader().setMessage("此菜单已存在");
                return restResponse;
            }
            Date now =  new Date();
            authorityPojo.setUpdateTime(now);
            if(authorityPojo.getStatus()==null) {
                authorityPojo.setStatus(0);
            }
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            authorityPojo.setUpdateUser(userPojo.getUsername());
            this.authorityMapper.editAuthority(authorityPojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("更新菜单成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新菜单出现异常", e);
            throw e;
        }
        return restResponse;
    }



    /**
     * 删除菜单
     *
     * @param authorityPojo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse deleteAuthority(AuthorityPojo authorityPojo) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新菜单!");
        RestResponse restResponse = new RestResponse();
        try {
            authorityPojo = this.authorityMapper.getAuthorityInfo(authorityPojo);
            if(authorityPojo.getPid()==0){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.NO_EDIT_DELETE);
                restResponse.getResponseHeader().setMessage("一级菜单不可删除");
                return restResponse;
            }
            Integer flag = this.authorityMapper.checkIsRoleRelate(authorityPojo);
            if (flag!=0) {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.RELATION_DATA);
                restResponse.getResponseHeader().setMessage("此菜单有角色与之关联");
                return restResponse;
            }
            this.authorityMapper.deleteAuthority(authorityPojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("删除菜单成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新菜单出现异常", e);
            throw e;
        }
        return restResponse;
    }

}
