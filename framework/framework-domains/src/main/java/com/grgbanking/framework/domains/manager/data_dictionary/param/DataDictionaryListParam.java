package com.grgbanking.framework.domains.manager.data_dictionary.param;

import java.io.Serializable;

/**
 * Created by zhangweihua on 2018/1/15.
 */
public class DataDictionaryListParam implements Serializable {
    private String name;

    private Integer pageNo;

    private Integer pageSize;

    private Integer currentCount;

    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
