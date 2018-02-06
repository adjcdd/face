package com.grgbanking.framework.domains.manager.common.json;


import java.io.Serializable;
import java.util.List;

/**
 * Created by wyf on 2017/8/6.
 */
public class PaginationJson implements Serializable{

    private List<?> data;

    private int pageNo = 1;

    private long totalCount;

    private int totalPage;

    private int pageSize = 10;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
