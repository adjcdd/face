package com.grgbanking.framework.domains.manager.user.param;

import java.io.Serializable;

/**
 * Created by wjian17 on 2017/10/11.
 */
public class UserAddParam implements Serializable {

	private String username;

//	private String password;

	private String name;

	private Integer status;

	private String telphone;

	private String email;

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

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
