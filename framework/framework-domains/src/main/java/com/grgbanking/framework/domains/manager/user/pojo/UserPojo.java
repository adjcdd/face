package com.grgbanking.framework.domains.manager.user.pojo;

import com.grgbanking.framework.domains.manager.role.pojo.RolePojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wyf on 2017/8/1.
 */
public class UserPojo implements Serializable{

    private Long id;

    private String name;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String createUser;

    private String updateUser;

    private String telphone;

    private String email;

    private List<RolePojo> rolePojoList;

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

    public List<RolePojo> getRolePojoList() {
        return rolePojoList;
    }

    public void setRolePojoList(List<RolePojo> rolePojoList) {
        this.rolePojoList = rolePojoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
