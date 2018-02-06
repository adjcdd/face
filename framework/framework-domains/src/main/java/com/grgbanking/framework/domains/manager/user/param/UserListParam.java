package com.grgbanking.framework.domains.manager.user.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class UserListParam implements Serializable {

	private String name;

	private String username;

	private Integer pageNo;

	private Integer pageSize;

	private Integer currentCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
