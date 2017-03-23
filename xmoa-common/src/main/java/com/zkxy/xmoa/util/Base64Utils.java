package com.zkxy.xmoa.util;


import org.apache.commons.codec.binary.Base64;


/**
 * <p>Description: BASE64 编码解码工具包 【依赖javabase64-1.3.1.jar】【是用于传输8bit字节代码最常用的编码方式。】</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class Base64Utils {


    /**
     * <p><b>Description : <b/>二进制数据编码为BASE64字符串</p>
     * @param
     *     bytes  byte[]
     * @return
     *     byte[]
     */
    public static String encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * <p><b>Description : <b/>BASE64字符串解码为二进制数据</p>
     * @param
     *     base64
     * @return
     *     byte[]
     */
    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64.getBytes());
    }

    /**
     * <p><b>Description : <b/>base64解码</p>
     * @param
     *     bytes byte[]
     * @return
     *     byte[]
     */
    public static byte[] decode(byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }



    public static void main(String[] args) {
        String data = "test";
        System.out.println("加密"+encode("ee2e7a9d719f1d219eea25bc56c7fa23".getBytes()));
        System.out.println("解密"+new String( decode( encode("ee2e7a9d719f1d219eea25bc56c7fa23".getBytes())  )) );
        System.out.println("加密 test:"+encode(data.toString().getBytes()));
    }
}
