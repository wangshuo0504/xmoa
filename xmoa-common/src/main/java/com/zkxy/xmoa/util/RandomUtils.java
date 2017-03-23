package com.zkxy.xmoa.util;

import java.security.SecureRandom;

/**
 * @Description 生成暗号
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class RandomUtils {
	
	public static final String allChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";
	
	/** 
	 * <p><b>Description : <b/>返回一个定长的随机字符串(只包含大写字母数字)</p>
	 * 
	 * @param length 随机字符串长度 
	 * @return 随机字符串 
	 */ 
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}
	
	/**
	 * <p><b>Description : <b/>返回一个定长的随机字符串(只包含数字)</p>
	 * 
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumberString(int length) {
		StringBuffer sb = new StringBuffer();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	/**
	 * <p><b>Description : <b/>返回6个字符串的邮箱 【验证码】 (只包含大写字母数字)</p>
	 *
	 * @param email 邮箱号码
	 * @return 随机字符串
	 */
	public static String emailActiveValiDateCode(String email){
		 String code = generateString(6);
		 String valieDateCode = MD5Util.generateMD5(email, code);
		 return valieDateCode;
	}
	
	public static void main(String[] args){
		System.out.println(generateString(6));
	}
	
}
