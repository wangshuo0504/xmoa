package com.zkxy.xmoa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * <p>Title: </p>
 * <p>Description: md5加密 【该加密算法是单向加密，即加密的数据不能再通过解密还原。】</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class MD5Util {

	public static String generateMD5(String s,String key) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		String str = s + key;
		digest.update(str.getBytes());
		return ByteUtils.byteArray2HexString(digest.digest());
	}
	
	/**
	 * 固定加密，不带随机数
	 * @创建人：周礼
	 * @创建时间：2015年11月20日
	 * @创建目的：【】
	 * @修改目的：【修改人：，修改时间：】
	 * @param s
	 * @return
	 */
	public static String generateMD5(String str) {
	    MessageDigest digest;
	    try {
	        digest = MessageDigest.getInstance("MD5");
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException();
	    }
	    digest.update(str.getBytes());
	    return ByteUtils.byteArray2HexString(digest.digest());
	}
	
	
}
