package com.a.easybuy.pojo;

import java.util.List;

public class PageInfo<E>{
    private Integer pageNow;
    private Integer pageSize;
    private int total;
    private int pages;
    private List<E> list;

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if(total > 0){
            this.pages = total%this.pageSize == 0 ? total / this.pageSize:total/pageSize -1;
        }
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
