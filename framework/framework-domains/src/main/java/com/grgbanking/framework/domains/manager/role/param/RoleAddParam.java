package com.grgbanking.framework.domains.manager.role.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class RoleAddParam implements Serializable{

	private String roleName;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
