package com.grgbanking.framework.domains.manager.user.json;

import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjian17 on 2017/10/14.
 */
public class UserRoleListJson implements Serializable {

	private List<RolePojo> rolePojoReleaseList;//所有角色

	private List<RolePojo> rolePojoSelectList;

	public List<RolePojo> getRolePojoReleaseList() {
		return rolePojoReleaseList;
	}

	public void setRolePojoReleaseList(List<RolePojo> rolePojoReleaseList) {
		this.rolePojoReleaseList = rolePojoReleaseList;
	}

	public List<RolePojo> getRolePojoSelectList() {
		return rolePojoSelectList;
	}

	public void setRolePojoSelectList(List<RolePojo> rolePojoSelectList) {
		this.rolePojoSelectList = rolePojoSelectList;
	}
}
