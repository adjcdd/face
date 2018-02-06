package com.grgbanking.framework.domains.manager.role.pojo;

import com.grgbanking.framework.domains.manager.authority.pojo.AuthorityPojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wjian17 on 2017/10/9.
 */
public class RolePojo implements Serializable {

	private Long id;

	private String roleName;

	private Date createTime;

	private Date updateTime;

	private Integer status;

	private String createUser;

	private String updateUser;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	private List<AuthorityPojo> authorityPojoList;

	public List<AuthorityPojo> getAuthorityPojoList() {
		return authorityPojoList;
	}

	public void setAuthorityPojoList(List<AuthorityPojo> authorityPojoList) {
		this.authorityPojoList = authorityPojoList;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		return (int) (this.id + this.roleName.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		RolePojo rolePojo = (RolePojo)obj;
		return this.roleName.equals(rolePojo.getRoleName())&&this.getId()==rolePojo.getId();
	}
}
