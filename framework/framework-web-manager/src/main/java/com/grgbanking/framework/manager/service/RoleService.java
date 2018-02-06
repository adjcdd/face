package com.grgbanking.framework.manager.service;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.manager.authority.pojo.AuthorityPojo;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.domains.manager.role.param.RoleAddParam;
import com.grgbanking.framework.domains.manager.role.param.RoleDeleteParam;
import com.grgbanking.framework.domains.manager.role.param.RoleListParam;
import com.grgbanking.framework.domains.manager.role.param.RoleUpdateParam;
import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;
import com.grgbanking.framework.domains.manager.user.pojo.UserPojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.mapper.RoleMapper;
import com.grgbanking.framework.manager.session.HttpSessionTool;
import com.grgbanking.framework.util.page.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wyf on 2017/8/26.
 */
@Service("roleService")
public class RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询角色列表
     *
     * @param roleListParam
     * @return
     * @throws Exception
     */
    public RestResponse getRoleList(RoleListParam roleListParam,HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询角色列表!");
        RestResponse restResponse = new RestResponse();
        try {
            roleListParam.setCurrentCount((roleListParam.getPageNo() - 1) * roleListParam.getPageSize());
            //清除普通管理员角色
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            List<RolePojo> rolePojoList = new ArrayList<RolePojo>();
            long totalCount = 0;
            if("admin".equals(userPojo.getUsername())){
                rolePojoList = this.roleMapper.getRoleListByAdmin(roleListParam);
                totalCount = this.roleMapper.getRoleCountByAdmin(roleListParam);
            }else{
                rolePojoList = this.roleMapper.getRoleList(roleListParam);
                totalCount = this.roleMapper.getRoleCount(roleListParam);
            }
            PaginationJson page = new PaginationJson();
            page.setPageNo(roleListParam.getPageNo());
            page.setPageSize(roleListParam.getPageSize());
            page.setData(rolePojoList);
            page.setTotalCount(totalCount);
            page.setTotalPage(PageUtil.calTotalPage(totalCount, roleListParam.getPageSize()));
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(page);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询角色列表出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 查询所有角色
     * @return
     * @throws Exception
     */
    public RestResponse getAllRole(RolePojo rolePojo) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询所有角色!");
        RestResponse restResponse = new RestResponse();
        try {
            List<RolePojo> RolePojoList = this.roleMapper.getAllRole(rolePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(RolePojoList);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询角色列表出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 查询角色信息【带菜单】
     * @return
     * @throws Exception
     */
    public RestResponse getRoleInfo(RolePojo rolePojo,HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询角色详情!");
        RestResponse restResponse = new RestResponse();
        try {
            rolePojo = this.roleMapper.getRoleInfo(rolePojo);
            List<AuthorityPojo> authorityPojoList = rolePojo.getAuthorityPojoList();
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            if(!"admin".equals(userPojo.getUsername())){
                List<AuthorityPojo> authorityPojos = new ArrayList<AuthorityPojo>();//admin独有的权限
                for (AuthorityPojo authorityPojo:authorityPojoList) {
                    if(authorityPojo.getAuthorityLevel()==0){
                        authorityPojos.add(authorityPojo);
                    }
                }
                authorityPojoList.removeAll(authorityPojos);
            }
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
            restResponse.setResponseBody(rolePojo);
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询角色详情出现异常", e);
            throw e;
        }
        return restResponse;
    }

	/**
	 * 查询角色信息
	 * @return
	 * @throws Exception
	 */
	public RestResponse getRoleForEdit(RolePojo rolePojo) throws Exception {
		logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询角色详情!");
		RestResponse restResponse = new RestResponse();
		try {
			rolePojo = this.roleMapper.getRoleForEdit(rolePojo);
			restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
			restResponse.getResponseHeader().setMessage("查询成功");
			restResponse.setResponseBody(rolePojo);
		} catch (Exception e) {
			logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询角色详情出现异常", e);
			throw e;
		}
		return restResponse;
	}


    /**
     * 新增角色
     *
     * @param roleAddParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse addRole(RoleAddParam roleAddParam,HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增角色!");
        RestResponse restResponse = new RestResponse();
        try {
            RolePojo rolePojo = new RolePojo();
            BeanUtils.copyProperties(roleAddParam,rolePojo);
            Integer flag = this.roleMapper.checkIsExistRole(rolePojo);
            if(flag!=0){
                restResponse.getResponseHeader().setErrorCode(ErrorCode.DUPLICATE_DATA);
                restResponse.getResponseHeader().setMessage("角色已存在");
                return restResponse;
            }
            Date now  = new Date();
            rolePojo.setCreateTime(now);
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            rolePojo.setCreateUser(userPojo.getUsername());
            this.roleMapper.addRole(rolePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("新增角色成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增角色出现异常", e);
            throw e;
        }
        return restResponse;
    }

    /**
     * 更新角色
     *
     * @param roleUpdateParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse updateRole(RoleUpdateParam roleUpdateParam,HttpServletRequest request) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新角色!");
        RestResponse restResponse = new RestResponse();
        try {
            RolePojo rolePojo = new RolePojo();
            BeanUtils.copyProperties(roleUpdateParam, rolePojo);
            Integer flag = this.roleMapper.checkIsExistRole(rolePojo);
            if (flag!=0&&rolePojo.getStatus()== 1) {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.DUPLICATE_DATA);
                restResponse.getResponseHeader().setMessage("此角色已存在");
                return restResponse;
            }
            Date now =  new Date();
            rolePojo.setUpdateTime(now);
            UserPojo userPojo = HttpSessionTool.getValue(request,"user");
            rolePojo.setUpdateUser(userPojo.getUsername());
            this.roleMapper.editRole(rolePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("更新角色成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新角色出现异常", e);
            throw e;
        }
        return restResponse;
    }



    /**
     * 删除角色
     *
     * @param roleDeleteParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse deleteRole(RoleDeleteParam roleDeleteParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新角色!");
        RestResponse restResponse = new RestResponse();
        try {
            RolePojo rolePojo =  new RolePojo();
            rolePojo.setId(roleDeleteParam.getId());
            Integer flag = 0;
            if(roleDeleteParam.getDeleteIgnoreRelationship()==null||"".equals(roleDeleteParam.getDeleteIgnoreRelationship())){
                flag = this.roleMapper.checkIsUserRelate(rolePojo);
            }else{
                this.roleMapper.deleteRelateUser(rolePojo);
            }
            if (flag!=0) {
                restResponse.getResponseHeader().setErrorCode(ErrorCode.RELATION_DATA);
                restResponse.getResponseHeader().setMessage("此角色已有关联账户,是否删除");
                return restResponse;
            }
            this.roleMapper.deleteRelateAuthority(rolePojo);
            this.roleMapper.deleteRole(rolePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("删除角色成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新角色出现异常", e);
            throw e;
        }
        return restResponse;
    }


    /**
     * 角色授权
     *
     * @param rolePojo
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse roleAuthorite(RolePojo rolePojo) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始角色授权!");
        RestResponse restResponse = new RestResponse();
        try {
            this.roleMapper.deleteRelateAuthority(rolePojo);
            List<AuthorityPojo> authorityPojoList = rolePojo.getAuthorityPojoList();
            Long roleId = rolePojo.getId();
            for(AuthorityPojo authorityPojo:authorityPojoList) {
                this.roleMapper.insertRoleAuthority(roleId,authorityPojo.getId());
            }
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("角色授权成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "角色授权出现异常", e);
            throw e;
        }
        return restResponse;
    }

}
