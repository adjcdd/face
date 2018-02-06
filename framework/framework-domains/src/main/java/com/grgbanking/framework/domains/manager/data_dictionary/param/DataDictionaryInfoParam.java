package com.grgbanking.framework.domains.manager.data_dictionary.param;

import java.io.Serializable;

/**
 * Created by zhangweihua on 2018/1/15.
 */
public class DataDictionaryInfoParam implements Serializable {

    private String code;

    private String name;

    private Byte type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
