package com.grgbanking.framework.domains.manager.user.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class UserUpdateParam implements Serializable {

	private String username;

	private String oriPassword;

	private String rePassword;

	private String password;

	private String name;

	private Long id;

	private Integer status;

	private String telphone;

	private String email;

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOriPassword() {
		return oriPassword;
	}

	public void setOriPassword(String oriPassword) {
		this.oriPassword = oriPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
