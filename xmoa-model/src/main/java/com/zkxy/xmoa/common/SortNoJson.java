package com.zkxy.xmoa.common;

import java.util.List;

/**
 * Created by Administrator on 2017-3-8.
 */
public class SortNoJson {


   private String sortType;

    /**
     * 例如 1:4300000000 这样的string 根据sortType的类型 说明排序的是
     * 什么对象 1是排序号 4300000000是id
     */
   private List<String> resultList;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}
