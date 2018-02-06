package com.grgbanking.framework.domains.manager.authority.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class AuthorityUpdateParam implements Serializable {

	private Long id;

	private String authorityName;

	private String url;

	private String img;

	private Long pid;

	private String className;

	private Integer status;

	private Integer authorityLevel;

	public Integer getAuthorityLevel() {
		return authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
