/**
 * 
 */
package com.zkxy.xmoa.util;


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * <p>Description: bean对象工具类 </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public final class BeanObjectUtil {

    /**
     * <p><b>Description : <b/>数据复制</p>
     * 
     * @param arg0
     *            要被填充的类
     * @param arg1
     *            填充类
     */
    public static void copysProperties(Object arg0, Object arg1) {
        copysProperties(arg0, arg1, false);
    }

    /**
     * <p><b>Description : <b/>数据复制</p>
     * @param arg0
     *            要被填充的类
     * @param arg1
     *            填充类
     * @param IgnoreNull
     *            如果填充类属性为null,则不复制
     */
    public static void copysProperties(Object arg0, Object arg1, boolean IgnoreNull) {
        try {
            if (arg0 == null || arg1 == null) {
                return;
            }
            int length1 = 0;
            int length0 = 0;
            Method[] methods1 = arg1.getClass().getMethods();
            Method[] methods0 = arg0.getClass().getMethods();

            if (methods1 != null && methods1.length > 0) {
                length1 = methods1.length;
            }
            if (methods0 != null && methods0.length > 0) {
                length0 = methods0.length;
            }
            for (int i = 0; i < length1; i++ ) {
                try {
                    Method method = methods1[i];
                    if (method.getName().startsWith("get")
                        && method.getParameterTypes().length == 0) {
                        String fieldName = method.getName();
                        fieldName = fieldName.substring(3, 4).toUpperCase()
                                    + fieldName.substring(4);

                        for (int j = 0; j < length0; j++ ) {
                            Method method0 = methods0[j];
                            if (("set" + fieldName).equals(method0.getName())) {
                                Object value = method.invoke(arg1, new Object[] {});

                                // 1.如果IgnoreNull为true,代表忽略Null,如果取值不为null才调set方法
                                // 2.如果IgnoreNull 为false 不管怎么样都复制属性
                                if ((value != null && IgnoreNull) || !IgnoreNull) {
                                    method0.invoke(arg0, new Object[] {value});
                                }

                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p><b>Description : <b/>Bean去空格处理</p>
     * 
     * @param
     *       bean
     */
    public static Object convetorTrimBean(Object bean)
        throws Exception {
        Class<? extends Object> type = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++ ) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null && StringUtils.trim(result.toString()).length() != 0) {
                    // 判断是否有空格
                    if (StringUtil.containsBlank(result.toString()))
                        throw new Exception("输入内容有空格,请重新输入");
                    Method writeMethod = descriptor.getWriteMethod();
                    writeMethod.invoke(bean, new Object[] {StringUtils.trim(result.toString())});
                }
            }
        }
        return bean;
    }

    /**
     * <p><b>Description : <b/> Bean String[]转换 </p>
     * 
     * @param
     *        bean
     */
    public static String[] beanStringConvetor(Object bean, String[] attr) {
        Class<? extends Object> type = bean.getClass();
        String[] retVals = new String[attr.length];
        try {
            String attrVals = "";
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++ ) {
                attrVals = "";
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        attrVals = "" + result;
                    }
                    for (int j = 0; j < attr.length; j++ ) {
                        if (StringUtils.equals(readMethod.getName().split("get")[1].toUpperCase(),
                            attr[j].toUpperCase())) {
                            retVals[j] = attrVals;
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {}
        return retVals;
    }

    /**
     * <p><b>Description : <b/> 如果返回为空对象则转换为自定义字符串</p>
     *  @param
     *        object  对象
     *  @param
     *        string 自定义字符串
     */
    public static String nullObjectFormat(Object object, String string) {
        String str;
        if (object == null) {
            str = string;
        } else {
            str = object.toString();
        }
        return str;
    }

    /**
     * <p><b>Description : <b/>打印对象属性值</p>
     * @param
     *    ob 对象
     */
    public static String printObjectParamValue(Object ob) {
        Class cla = ob.getClass();
        // Method[] method=cla.getDeclaredMethods();
        Field[] field = cla.getDeclaredFields();
        StringBuffer strbuff = new StringBuffer();
        for (int i = 0; i < field.length; i++ ) {
            String value = "";
            try {
                String name = field[i].getName();
                value = cla.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), null).invoke(ob, null).toString();
            } catch (Exception e) {

                e.printStackTrace();
            }
            strbuff.append(field[i].getName()).append(" [").append(value).append(" ] ");
        }
        return strbuff.toString();
    }

    /**
     * <p><b>Description : <b/>打印对象属性值</p>
     * @param
     *      type 对象
     * @param
     *      map  属性值
     */
    public static Object convertMap(Class type, Map<String, Object> map) throws Exception {
        // 创建 JavaBean 对象 
        Object obj = null; 
  
            // 获取类属性 
            BeanInfo beanInfo = Introspector.getBeanInfo(type); 

            obj = type.newInstance(); 

            // 给 JavaBean 对象的属性赋值 
            PropertyDescriptor[] propertyDescriptors = beanInfo 
                    .getPropertyDescriptors(); 
            for (int i = 0; i < propertyDescriptors.length; i++) 
            { 
                PropertyDescriptor descriptor = propertyDescriptors[i]; 
                String propertyName = descriptor.getName().toLowerCase(Locale.getDefault());

                if (map.containsKey(propertyName)) 
                { 
                    String value = ConvertUtils.convert(map.get(propertyName)); 
                    Object[] args = new Object[1]; 
                    args[0] = ConvertUtils.convert(value, descriptor 
                            .getPropertyType()); 

                    descriptor.getWriteMethod().invoke(obj, args); 

                } 
            } 
        return obj; 
    }
    
    //
    /**
     * <p><b>Description : <b/>Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map</p>
     * @param
     *      obj 对象
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

}
