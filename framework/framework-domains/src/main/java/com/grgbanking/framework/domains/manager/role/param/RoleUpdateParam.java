package com.grgbanking.framework.domains.manager.role.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class RoleUpdateParam implements Serializable {

	private Long id;

	private String roleName;

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
