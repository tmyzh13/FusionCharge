package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;

public class Page implements Serializable {

    /** 当前页码 **/
    public int pageNo;

    /** 总页数 **/
    public int pageCount;

    /** 每页个数 **/
    public int pageSize;

    /** 数据总个数 **/
    public int totalCount;

    public Page(int pageNo, int pageSize, int pageCount) {
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.pageSize = pageSize;
    }

}