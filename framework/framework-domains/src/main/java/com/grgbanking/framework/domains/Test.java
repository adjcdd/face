package com.grgbanking.framework.domains;

import com.grgbanking.framework.domains.common.annotation.StoreSecurity;

import java.io.Serializable;

/**
 * Created by wyf on 2017/5/25.
 */
public class Test implements Serializable {

    private long id;

    @StoreSecurity
    private String name;

    private int age;

    @StoreSecurity
    private byte[] feature;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getFeature() {
        return feature;
    }

    public void setFeature(byte[] feature) {
        this.feature = feature;
    }
}
