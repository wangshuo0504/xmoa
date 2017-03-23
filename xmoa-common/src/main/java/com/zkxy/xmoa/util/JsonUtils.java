package com.zkxy.xmoa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p>Description: Json工具类</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class JsonUtils {
	private static ObjectMapper om = new ObjectMapper();
	static {
		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * <p><b>Description : <b/>将json字符串 转换成 map </p>
	 * @param
	 *    json 字符串
	 */
	public static Map<String, String> jsonToMap(String json) {
		try {
			return om.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p><b>Description : <b/>将 map 转换成 json字符串 </p>
	 * @param
	 *    map
	 */
	public static <T> String map2json(Map<String, T> map) {
		return obj2json(map);
	}

	/**
	 * <p><b>Description : <b/>将 Object 转换成 json字符串 </p>
	 * @param
	 *    o
	 */
	public static String obj2json(Object o){
		try {
			return om.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p><b>Description : <b/>将 json字符串  转换成  对应的 bean</p>
	 * @param
	 *    json  字符串
	 * @param
	 *    valueType 对象
	 */
	public static  <T> T json2Obj(String json, Class<T> valueType){
		try {
			return om.readValue(json, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p><b>Description : <b/>将 json字符串  转换成  对应的 bean
	 *   如需转换为list,只需要 List<User> list = json2Obj(value,  new TypeReference<ArrayList<User>>(){});即可
	 * </p>
	 * @param
	 *    json  字符串
	 * @param
	 *    valueTypeRef 对象
	 */
	public static  <T> T json2Obj(String json, TypeReference<T> valueTypeRef){
		try {
			return om.readValue(json, valueTypeRef);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p><b>Description : <b/>将 json字符串  拼接成 【&key=value】</p>
	 * @param
	 *    json  字符串
	 */
	public static String getSignData(String json){
		Map<String,String> jsonMap = jsonToMap(json);
		String result = null;
		if(jsonMap != null) {
			Map<String,String> map = new TreeMap<String, String>();
			Set<Map.Entry<String, String>> set = jsonMap.entrySet();
			for (Map.Entry<String, String> entry : set) {
				map.put(entry.getKey(), entry.getValue());
			}
			StringBuilder sb = new StringBuilder();
			for (String key : map.keySet()) {
				sb.append("&").append(key).append("=");
				sb.append(map.get(key));
			}
			result = sb.substring(1);
		}
		return result;
	}


	/*static class DateJsonValueProcessor implements JsonValueProcessor {
		private String format = "yyyy-MM-dd HH:mm:ss";

		public DateJsonValueProcessor() {
		}

		public DateJsonValueProcessor(String format) {
			this.format = format;
		}

		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			String[] obj = {};
			if (value instanceof Date[]) {
				SimpleDateFormat sf = new SimpleDateFormat(format);
				Date[] dates = (Date[]) value;
				obj = new String[dates.length];
				for (int i = 0; i < dates.length; i++) {
					obj[i] = sf.format(dates[i]);
				}
			}
			return obj;
		}

		public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			if (value instanceof Date) {
				String str = new SimpleDateFormat(format).format((Date) value);
				return str;
			}
			if(value == null)
				return "";
			return value.toString();
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

	}*/

}
