package com.zkxy.xmoa.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.ByteBuffer;
import java.security.Key;

/**
 * @Description  DES加密工具类 【该加密算法是可逆的，解密方可以通过与加密方约定的密钥匙进行解密。】
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class DesEdeUtil {

    //算法名称
    public static final String KEY_ALGORITHM = "DES";

    /**
     * <p><b>Description : <b/>生成密钥</p>
     */
    public static byte[] generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        keyGenerator.init(56); //des 必须是56, 此初始方法不必须调用
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * <p><b>Description : <b/>还原密钥</p>
     * @param key  密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        DESKeySpec des = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(des);
        return secretKey;
    }

    /**
     * <p><b>Description : <b/>生产密钥</p>
     * @param
     *     paramArrayOfByte  byte[] enKey 密钥
     */
    private static final Key makeDesKey(byte[] paramArrayOfByte) throws Exception {
        DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramArrayOfByte);
        SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance("DESede");
        return localSecretKeyFactory.generateSecret(localDESedeKeySpec);
    }

    /**
     * <p><b>Description : <b/>加密</p>
     * @param
     *     paramArrayOfByte1  byte[] 需要加密的字符串
     * @param
     *     paramArrayOfByte2  byte[] enKey 3-DES加密密钥
     */
    public static byte[] desEdeEncrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        if (paramArrayOfByte2.length < 24) {
            paramArrayOfByte2 = make3DesKey(paramArrayOfByte2);
        }
        Key localKey = makeDesKey(paramArrayOfByte2);
        Cipher localCipher = Cipher.getInstance("DESede");
        localCipher.init(1, localKey);
        return localCipher.doFinal(paramArrayOfByte1);
    }

    /**
     * <p><b>Description : <b/>解密</p>
     * @param
     *     paramArrayOfByte1  byte[] 需要解密的字符串
     * @param
     *     paramArrayOfByte2  byte[] enKey 3-DES加密密钥
     */
    public static byte[] desEdeDecrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        if (paramArrayOfByte2.length < 24) {
            paramArrayOfByte2 = make3DesKey(paramArrayOfByte2);
        }
        Key localKey = makeDesKey(paramArrayOfByte2);
        Cipher localCipher = Cipher.getInstance("DESede");
        localCipher.init(2, localKey);
        return localCipher.doFinal(paramArrayOfByte1);
    }

    /**
     * <p><b>Description : <b/>ECB解密</p>
     * @param
     *     paramArrayOfByte1  byte[] 需要解密的字符串
     * @param
     *     paramArrayOfByte2  byte[] enKey 3-DES加密密钥
     */
    public static byte[] desEdeEcbDecrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        if (paramArrayOfByte2.length < 24) {
            paramArrayOfByte2 = make3DesKey(paramArrayOfByte2);
        }
        Key localKey = makeDesKey(paramArrayOfByte2);
        //DESede/ECB/PKCS5Padding 表示的是: 算法名称/加密模式/填充方式
        Cipher localCipher = Cipher.getInstance("DESede/ECB/NoPadding");
        localCipher.init(2, localKey);
        return localCipher.doFinal(paramArrayOfByte1);
    }

    /**
     * <p><b>Description : <b/>ECB解密</p>
     * @param
     *     paramArrayOfByte1  byte[] 需要解密的字符串
     * @param
     *     paramArrayOfByte2  byte[] enKey 3-DES加密密钥
     */
    public static byte[] desEdeEcbPkcs5Decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) throws Exception {
        if (paramArrayOfByte2.length < 24) {
            paramArrayOfByte2 = make3DesKey(paramArrayOfByte2);
        }
        Key localKey = makeDesKey(paramArrayOfByte2);
        //DESede/ECB/PKCS5Padding 表示的是: 算法名称/加密模式/填充方式
        Cipher localCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        localCipher.init(2, localKey);
        return localCipher.doFinal(paramArrayOfByte1);
    }

    /**
     * <p><b>Description : <b/>生产 二进制数据编码为BASE64字符串 </p>
     * @param
     *     paramArrayOfByte
     */
    public static byte[] make3DesKey(byte[] paramArrayOfByte) {
        byte[] arrayOfByte1 = null;
        byte[] arrayOfByte2 = new byte[8];

        //通过包装的方法创建的缓冲区保留了被包装数组内保存的数据
        /*如果要将一个字符串存入ByteBuffer,可以如下操作:
        String sendString="你好,服务器. ";
        ByteBuffer sendBuffer=ByteBuffer.wrap(sendString.getBytes("UTF-16"));*/
        ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
        localByteBuffer.get(arrayOfByte2); //读取bytebuffer中数据写入 arrayOfByte2[]
        localByteBuffer.clear();//清除缓冲区
        localByteBuffer = ByteBuffer.allocate(24);//创建一个容量为24字节的ByteBuffer,如果发现创建的缓冲区容量太小,唯一的选择就是重新创建一个大小合适的缓冲区
        localByteBuffer.put(paramArrayOfByte);////从paramArrayOfByte[]写入bytebuffer
        localByteBuffer.put(arrayOfByte2);
        localByteBuffer.flip();//准备将数据读出buffer
        arrayOfByte1 = localByteBuffer.array();//返回的 array 长度为 ByteBuffer allocate的长度，并不是里面所含的内容的长度
        localByteBuffer.clear();
        return arrayOfByte1;
    }

    public static void main(String args[]) {
        //密钥
        String deskey = "c028a6e6379e4a19c028a6e6379e4a19";
        //需要加密的数据
//      String dataStr1 = "18628130015|072489810000013|07241022";
        String dataStr1 ="mj5572425";
        // 十六进制 转换 byte[]
        byte[] deskeyByte = ByteUtils.hexString2ByteArray(deskey);

        String pin1 = "3536935C6A490971CB41F9363AA881996105FDE9D2B28C3423A7E70889C5C16E";
        try {

            deskeyByte = generateKey();
            System.out.println("自动生成的密钥:"+ toKey(deskeyByte) );

            String result = ByteUtils.byteArray2HexString(desEdeEncrypt(dataStr1.getBytes(), deskeyByte));
            System.out.println("加密后：" + result);

            byte[] mingwen = desEdeEcbDecrypt(ByteUtils.hexString2ByteArray(result), deskeyByte);
            System.out.println("解密后:" + new String(mingwen).trim());
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}