package com.zkxy.xmoa.util;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Description 字符串处理类
 * @Copyright Copyright (c) 2015</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public final class StringUtil {

    /**
     * <p><b>Description : <b/> 字符串右侧填充指定字符</p>
     * @param
     *        value 指定的字符串
     * @param
     *        totalLen  填充后长度
     * @param
     *        fillChar 填充字符
     * @return
     */
    public static String rightFill(String value, int totalLen, char fillChar) {
        int fillLen = totalLen - value.length();
        if (fillLen <= 0) return value;
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        for (int i = 0; i < fillLen; i++ ) {
            sb.append(fillChar);
        }
        return sb.toString();
    }

    /**
     * <p><b>Description : <b/> 字符串左侧填充指定字符 </p>
     * @param
     *        value 指定的字符串
     * @param
     *        totalLen  填充后长度
     * @param
     *        fillChar 填充字符
     * @return
     */
    public static String leftFill(String value, int totalLen, char fillChar) {
        int fillLen = totalLen - value.length();
        if (fillLen <= 0) return value;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fillLen; i++ ) {
            sb.append(fillChar);
        }
        sb.append(value);
        return sb.toString();
    }

    /**
     * <p><b>Description : <b/>判断字符串等于空 </p>
     * @param
     *       str 字符串
     * @return
     *       boolean 返回类型
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * <p><b>Description : <b/> 判断字符串不等于空 </p>
     * @param
     *       str 字符串
     * @return
     *       boolean 返回类型
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * <p><b>Description : <b/> 字符串清空头尾空格 </p>
     * @param
     *       str 字符串

     */
    public static String trimNull(String str) {
        if (str == null)
            return "";
        else
            return str.trim();
    }

    /**
     * <p><b>Description : <b/>驼峰命名转换</p>
     * @param
     *        name  字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    
    /**
     * <p><b>Description : <b/> 判断字符中是否有空格 </p>
     * @param
     *        str 字符串
     */
    public static boolean containsBlank(String str) {
        Pattern pattern = Pattern.compile("[\\s]+");
        Matcher matcher = pattern.matcher(str);
        boolean flag = false;
        while (matcher.find()) {
            flag = true;
        }
        return flag;
    }

    /**
     * <p><b>Description : <b/> html特殊字符转换 </p>
     * @param
     *        str 字符串
     */
    public static String htmlspecialchars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        return str;
    }

    /**
     * <p><b>Description : <b/> 判断是否是手机号 </p>
     * @param
     *        mobile  手机号码
     */
    public static boolean isMobile(String mobile) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

    /**
     * <p><b>Description : <b/> 增加判断是否邮箱的方法 </p>
     * @param
     *        email
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    /**
     * <p><b>Description : <b/> 去掉小数点后多余0 </p>
     * @param
     *        s 字符串
     * @return
     */
    public static String subZeroAndDot(String s){    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;    
    }    

    /**
     * <p><b>Description : <b/>
     *  返回source中匹配正则表达式regex的所有子串
     *  示例：source = "取${person}付款当日${paymentTime}的${section}"  ，
     *  示例1：regex = "\\$\\{[^\\}]*\\}"， index = 0, 返回[${person}, ${paymentTime}, ${section}]
     *  示例2：regex = "\\$\\{([^\\}]*)\\}" ， index = 1, 返回[person, paymentTime, section]
     * </p>
     * @param
     *        source 字符串
     * @param
     *        regex   表达式
     * @param
     *        index    获取第几组数据
     * @return
     */
    public static List<String> getSubStrAryByReg(String source,String regex, int index){
        try{
            List<String> list = new ArrayList<String>();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                list.add(matcher.group(index));
            }
            return list;
        }catch(Exception ex){
            return null;
        }
    }

}
