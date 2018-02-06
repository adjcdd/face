package com.grgbanking.framework.domains.manager.user.json;

import java.io.Serializable;

/**
 * Created by wyf on 2017/8/12.
 */
public class CookieUserJson implements Serializable{

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
