package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.manager.role.param.RoleListParam;
import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("RoleMapper")
public interface RoleMapper extends BaseMapper {

	List<RolePojo> getRoleList(RoleListParam roleListParam);

	List<RolePojo> getRoleListByAdmin(RoleListParam roleListParam);

	List<RolePojo> getAllRole(RolePojo rolePojo);

	List<RolePojo> getAllRoleByAdmin(RolePojo rolePojo);

	RolePojo getRoleInfo(RolePojo rolePojo);

	RolePojo getRoleForEdit(RolePojo rolePojo);

	void deleteRelateAuthority(RolePojo RolePojo);

	void deleteRelateUser(RolePojo RolePojo);

	void deleteRole(RolePojo RolePojo);

	void insertRoleAuthority(@Param("role_id") Long roleId,@Param("authority_id") Long authorityId);

	void addRole(RolePojo RolePojo);

	void editRole(RolePojo RolePojo);

	Long getRoleCount(RoleListParam roleListParam);

	Long getRoleCountByAdmin(RoleListParam roleListParam);

	Integer checkIsExistRole(RolePojo RolePojo);

	Integer checkIsUserRelate(RolePojo RolePojo);


}
