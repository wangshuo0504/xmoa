/**
 * Created on 2015年10月9日 by ZhouMin
 */
package com.zkxy.xmoa.util;


import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * @Description 数字工具类
 * @Copyright Copyright (c) 2009</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class NumberUtil {

    /**
     * <p><b>Description : <b/>判断字符串是否为数字</p>
     * @param
     *      str
     */
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[1-9]+[0-9]*"); 
        return pattern.matcher(str).matches();    
     } 
    
    /**
     * <p><b>Description : <b/>【top/foot】千分之多少格式化</p>
     *  @param
     *      top  被除数
     *  @param
     *      foot  除数
     */
    public static String thousandths(Long top, Long foot) {
        if (foot == 0L) {
            return "0.00\u2030";
        }
        Double number = top * 1D / foot;
        System.out.println(number);
        Locale loc = new Locale("UTF-8");
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(loc);
        df.applyLocalizedPattern("0.00\u2030");
        return df.format(number);
    }
    
    /**
     * <p><b>Description : <b/> 【top/foot】 百分之多少格式化</p>
     *  @param
     *      top  被除数
     *  @param
     *      foot  除数
     */
    public static String percent(Long top, Long foot) {
        if (foot == 0L) {
            return "0.00%";
        }
        Double number = top * 1D / foot;
        Locale loc = new Locale("UTF-8");
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(loc);
        df.applyLocalizedPattern("0.00%");
        return df.format(number);
    }

    public static void main(String args[]) throws Exception{

        System.out.println( thousandths(8l,2l) );

    }

}
