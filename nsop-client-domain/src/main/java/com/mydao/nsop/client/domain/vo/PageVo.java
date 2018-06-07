package com.mydao.nsop.client.domain.vo;

public class PageVo {
    private Integer current;
    private Integer size;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public PageVo(Integer current,Integer size){
        this.current = current;
        this.size = size;
    }
}
