package com.zkxy.xmoa.util;


import java.math.BigInteger;

/**
 * <p>Description: BCD与十进制的转换 </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class BcdUtils {

	/**
	 * <p><b>Description : <b/>将字符串转换成二进制字符串 </p>
	 * @param
	 *    str 字符串
	 * @return
	 *   二进制字符串
	 */
	public static String StrToBinstr(String str) {
		char[] strChar=str.toCharArray();
		String result="";
		for(int i=0;i<strChar.length;i++){
			result +=Integer.toBinaryString(strChar[i]);
		}
		return result;
	}

	/**
	 * <p><b>Description : 2进制转换成10进制 <b/></p>
	 *
	 * @param
	 *    str 2进制字符串
	 * @return
	 *
	 */
	public static String toDecimalism(String str){
		BigInteger bigInteger = new BigInteger(str,2);
        return bigInteger.toString();
	}

	/**
	 * <p><b>Description : <b/>10进制串转为BCD码 </p>
	 * @param
	 *    value 10进制字符串
	 * @return
	 *    BCD码
	 */
	public static byte[] toBcd(String value) {
		int charpos = 0; // char where we start
		int bufpos = 0;
		byte[] buf = null;
		if (value.length() % 2 == 1) {
			buf = new byte[value.length() / 2 + 1];
			// for odd lengths we encode just the first digit in the first byte
			buf[0] = (byte) (value.charAt(0) - 48);
			charpos = 1;
			bufpos = 1;
		} else {
			buf = new byte[value.length() / 2];
		}
		// encode the rest of the string
		while (charpos < value.length()) {
			buf[bufpos] = (byte) (((value.charAt(charpos) - 48) << 4) | (value
					.charAt(charpos + 1) - 48));
			charpos += 2;
			bufpos++;
		}
		return buf;
	}


	public static void main(String[] args) {

		System.out.println( toBcd(toDecimalism(StrToBinstr("assa"))) );
	}

}
