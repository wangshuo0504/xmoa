package com.zkxy.xmoa.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目：怀化智慧交通大数据
 * 说明: 星期转换工具类
 * 日期：2016/8/24
 * 作者: zhiyi
 * 公司: 源本信息
 * 版本: v1.0
 * 修改记录:
 * 修改时间      修改人             说明
 */
public class WeekUtil {
    private static Map<String, String> weekMap = new HashMap<String, String>();
    private static String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    static {
        weekMap.put("0", "星期一");
        weekMap.put("1", "星期二");
        weekMap.put("2", "星期三");
        weekMap.put("3", "星期四");
        weekMap.put("4", "星期五");
        weekMap.put("5", "星期六");
        weekMap.put("6", "星期日");
    }

    public static String getWeek(String key) {
        return weekMap.get(key);
    }

    public static String getWeek(int key) {
        if (key >= 0 && key <= 6) {
            return weeks[key];
        }
        return null;
    }
}
