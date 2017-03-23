
package com.zkxy.xmoa.util;



import java.util.ArrayList;
import java.util.HashMap;
import com.zkxy.xmoa.system.model.DictData;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 *
 * @author 王硕
 * @version 1.0
 *          修改记录：
 *          修改序号，修改日期，修改人，修改内容
 */
public class DictCacheUtil {
    //数据字典缓存
    private static String DICT_CACHE = "dict-cache2";

    private static Map<String, Object> DICT_CACHE_MAP = new HashMap<String, Object>();

    /**
     * 单条字典数据放入缓存
     * <li>创建人：王硕</li>
     * <li>创建时间：2016年8月23日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     *
     * @param dictData
     */
    public static void putDictCache(DictData dictData) {
        if (StringUtil.isEmpty(dictData.getType()))
            return;
        List<DictData> list = (List<DictData>) DICT_CACHE_MAP.get(dictData.getType());
        if (list == null) {
            list = new ArrayList<DictData>();
            DICT_CACHE_MAP.put(dictData.getType(), list);
        } else
            list.add(dictData);
    }


    public static void putDictCaches(List<DictData> dictDataList) {
       for (DictData d :dictDataList){
           putDictCache(d);
       }
    }

    public static void putDictCaches(String type, List<DictData> dictDataList) {
        DICT_CACHE_MAP.put(type, dictDataList);
    }





    /**
     * 根据字典type或者字典数据列表
     * <li>创建人：王硕</li>
     * <li>创建时间：2016年8月23日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：ZhouMin，修改时间：改为缓存取不到则调用接口获取】</li>
     *
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<DictData> getDictDatasByType(String type) {
        if (StringUtil.isEmpty(type))
            return null;
        List<DictData> list = (List<com.zkxy.xmoa.system.model.DictData>) (List<DictData>) DICT_CACHE_MAP.get(type);
        return list;
    }

    /**
     * @param type
     * @param code
     * @return
     * <li>创建人：王硕</li>
     * <li>创建时间：2016年8月23日</li>
     * @创建目的【根据type和code获取字典项数据】
     * @修改目的【修改人：ZhouMin，修改时间：改为缓存取不到则调用接口获取】
     */
    public static DictData getDictDataByTypeAndCode(String type, String code) {
        List<DictData> dataList = getDictDatasByType(type);
        if (dataList != null) {
            for (DictData data : dataList) {
                if (data.getCode() != null && data.getCode().equals(code)) {
                    //返回需要的字典项数据。
                    return data;
                }
            }
        }
        return null;
    }


    public static DictData getDictDataByTypeAndCName(String type, String cname) {
        List<DictData> dataList = getDictDatasByType(type);
        if (dataList != null) {
            for (DictData data : dataList) {
                if (data.getName() != null && data.getName().equals(cname)) {
                    //返回需要的字典项数据。
                    return data;
                }
            }
        }
        return null;
    }


    /**
     * 根据类型和CODE获取CNAME
     *
     * @param type the type
     * @param code the code
     * @return the string
     * @创建人 何睿
     * @创建时间 2016 -4-11 14:19:22
     */
    public static String getCnameByTypeAndCode(String type, String code) {
        DictData data = getDictDataByTypeAndCode(type, code);
        if (data != null) {
            return data.getName();
        } else {
            return code;
        }
    }


    public static void clearCacheMap() {
        if (DICT_CACHE_MAP != null)
            DICT_CACHE_MAP.clear();
    }
}

