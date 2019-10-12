package com.dave.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页处理类
 * 
 * @author Dave20190826
 *
 * @param <T>
 */
public class PageObject<T> implements Serializable{
    private static final long serialVersionUID = 6780580291247550747L;
    /**当前页记录*/
    private List<T> records;
    /**当前页的页码值*/
    private Integer pageCurrent;
    /**总记录数*/
    private Integer rowCount;
    /**页面大小(每页最多显示多少条记录)*/
    private Integer pageSize;
    /**总页数(由总记录数和页面大小计算得到)*/
    private Integer pageCount;

    public List<T> getRecords() {
        return records;
    }
    public void setRecords(List<T> records) {
        this.records = records;
    }
    public Integer getPageCurrent() {
        return pageCurrent;
    }
    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }
    public Integer getRowCount() {
        return rowCount;
    }
    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getPageCount() {
        pageCount = (rowCount-1) / pageSize+1;
        return pageCount;
    }
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}