package com.grgbanking.framework.domains.manager.role.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class RoleListParam implements Serializable {

	private String roleName;

	private Integer pageNo;

	private Integer pageSize;

	private Integer currentCount;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
}
