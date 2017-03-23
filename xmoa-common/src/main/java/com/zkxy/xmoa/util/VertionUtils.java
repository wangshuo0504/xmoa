package com.zkxy.xmoa.util;


public class VertionUtils {

	/**
	 * <p><b>Description : <b/>当前版本和限制版本做比较 </p>
	 * 
	 * @param
	 *     vertion1  当前版本
	 * @param
	 *     vertion2  限制版本
	 * @return
	 *      【-1 小于】，【0等于】，【1大于】
	 */
	public static int compare(String vertion1, String vertion2) {
		String[] verArr1 = vertion1.split("[\\.Pp]");
		String[] verArr2 = vertion2.split("[\\.Pp]");
		int i = 0;
		for (; i < verArr1.length && i < verArr2.length; i++) {
			int ver1 = Integer.parseInt(verArr1[i]);
			int ver2 = Integer.parseInt(verArr2[i]);
			if (ver1 < ver2) {
				return -1;
			} else if (ver1 > ver2) {
				return 1;
			}
		}
		if (i < verArr1.length)
			return 1;
		if (i < verArr2.length)
			return -1;
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(compare("2.0.3","2.0.5"));
		System.out.println(compare("2.0.3p1","2.0.5"));
		System.out.println(compare("2.0.6","2.0.5"));
		System.out.println(compare("2.0.5","2.0.5"));
		System.out.println(compare("2.0.6","2.0.5P1"));
	}
}
