package com.zkxy.xmoa.common.constant;

/**
 * Created by zm on 2016/10/21.
 * 查询时间单位
 */
public enum  SearchTimeUnitEnum {
    HOUR("hour", "时"),
    DAY("day", "日"),
    WEEK("week", "周"),
    MONTH("month", "月"),
    YEAR("year", "年");

    private String code;
    private String name;

    SearchTimeUnitEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return this.name;
    }
}
