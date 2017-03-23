package com.zkxy.xmoa.common;

/**
 * Created by Administrator on 2017-3-8.
 */
public class ResponseJson {
    //code 1:success 0:fail
    private String code;

    private String msg;

    private  Object data;

    private  int  page;

    private  int  pageSize;

    private  int  totalNum;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public ResponseJson(String code) {
        this.code = code;
    }

    public ResponseJson(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseJson() {
    }
}
