package com.grgbanking.framework.domains.manager.authority.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class AuthorityAddParam implements Serializable {

	private String authorityName;

	private String className;

	private String img;

	private Long pid;

	private Integer status;

	private String url;

	private Integer authorityLevel;

	public Integer getAuthorityLevel() {
		return authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
}
