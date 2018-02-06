package com.grgbanking.framework.domains.manager.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wjian17 on 2017/10/9.
 */
public class AuthorityPojo implements Serializable, Comparable {

	private Long id;

	private String authorityName;

	private String url;

	private Integer authorityLevel;// 0-超级管理员，1-普通管理员

	private Date createTime;

	private Date updateTime;

	private Long pid;//父级菜单

	private String img;

	private String className;

	private Integer status;

	private String createUser;

	private String updateUser;

	private String pauthorityName;

	private Long sortId;

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	public String getPauthorityName() {
		return pauthorityName;
	}

	public void setPauthorityName(String pauthorityName) {
		this.pauthorityName = pauthorityName;
	}

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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getAuthorityLevel() {
		return authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode()*29 + authorityName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((AuthorityPojo)obj).getId());
	}

	public String parseString(){
		if (this.pid!=null&&this.pid == 0) {
				return "'i':'" + this.sortId + "','className':'" + this.className + "','img':'" + this.img + "','name':'" + this.authorityName + "'";
		} else {
				return "'url':'" + this.url + "','name':'" + this.authorityName + "'";
		}
	}

	@SuppressWarnings("Since15")
	public int compareTo(Object obj) {
		Long pid = ((AuthorityPojo) obj).getPid();
		if(this.getPid().equals(pid)){
			return Long.compare(this.getSortId(), ((AuthorityPojo) obj).getSortId());
		}else{
			return Long.compare(this.getPid(),pid);
		}

	}
}
