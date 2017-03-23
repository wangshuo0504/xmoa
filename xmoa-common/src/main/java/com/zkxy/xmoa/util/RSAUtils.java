package com.zkxy.xmoa.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description RSA公钥/私钥/签名工具包
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * <p><b>Description : <b/>生成密钥对(公钥和私钥)</p>
     *
     * @return
     *        Map
     *            PUBLIC_KEY     :  公钥的key
     *            RSAPrivateKey  :  私钥的key
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);


        String pubMod = publicKey.getModulus().toString(16);
        String pubExp = publicKey.getPublicExponent().toString(16);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 256 - pubExp.length(); i++) {
            buf.append("0");
        }
        String pubKey = pubMod + buf + pubExp;
        /*
        System.out.println("RSA key pair generated:");
        System.out.println("Public key modulus+exponenthexstr format:"+pubKey);
        System.out.println("Public key modulus hexstr format:" + pubMod);
        System.out.println();
        System.out.println("Public key exponent hexstr format:" + buf + pubExp);

        System.out.println("Private key modulus hexstr format:"
                + privateKey.getModulus().toString(16));
        System.out.println();
        System.out.println("Private key exponent hexstr format:"
                + privateKey.getPrivateExponent().toString(16));

        System.out.println("Public key modulus base64 format:"+ Base64Utils.encode(publicKey.getEncoded()));
        System.out.println("Private key modulus base64 format:"+  Base64Utils.encode(privateKey.getEncoded()));*/
        return keyMap;
    }

    /**
     * <p><b>Description : <b/>用私钥对信息生成数字签名</p>
     * @param
     *        data 已加密数据
     * @param
     *        privateKey 私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * <p><b>Description : <b/>校验数字签名</p>
     *
     * @param
     *        data 已加密数据
     * @param
     *        publicKey 公钥(BASE64编码)
     * @param
     *        sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * <p><b>Description : <b/>私钥解密</p>
     * @param
     *        encryptedData 已加密数据
     * @param
     *        privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p><b>Description : <b/>公钥解密 </p>
     * @param
     *        encryptedData 已加密数据
     * @param
     *        publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p><b>Description : <b/> 公钥加密 </p>
     * @param
     *        data 源数据
     * @param
     *        publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p><b>Description : <b/> 私钥加密 </p>
     * @param
     *        data 源数据
     * @param
     *        privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)  throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p><b>Description : <b/> 获取私钥 </p>
     * @param
     *         keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * <p><b>Description : <b/>获取公钥</p>
     * @param
     *         keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * <p><b>Description : <b/> 将16进制的私钥钥指数和模，getEncoded后转换为base64编码 </p>
     * @param
     *        privateKeyModHex
     * @param
     *        privateKeyExpHex
     * @return
     * @throws Exception
     */
    private static String converHexStrPrivateKey2Base64(String privateKeyModHex,String privateKeyExpHex) throws Exception {
        BigInteger mod = new BigInteger(privateKeyModHex, 16);
        BigInteger exp = new BigInteger(privateKeyExpHex, 16);
        RSAPrivateKeySpec spec = new RSAPrivateKeySpec(mod, exp);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(spec);
        return Base64Utils.encode(privateKey.getEncoded());
    }
    /**
     * <p><b>Description : <b/>将16进制的公钥指数和模，getEncoded后转换为base64编码 </p>
     * @param
     *         publicKeyModHex
     * @param
     *         publicKeyExpHex
     * @return
     * @throws Exception
     */
    private static String converHexStrPublicKey2Base64(String publicKeyModHex,String publicKeyExpHex) throws Exception {
        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(publicKeyModHex, 16), new BigInteger(publicKeyExpHex, 16));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return Base64Utils.encode(publicKey.getEncoded());
    }

    public static void main(String args[]) throws Exception{
        Map<String, Object> map = genKeyPair();
        String privateKey = getPrivateKey(map);
        String publicKey = getPublicKey(map);
        System.out.println("privateKey = "+privateKey);
        System.out.println("publicKey = "+publicKey);

      /*//使用mod和exp的形式测试
        String privateKeyMod="83beb97d3aa44b696b2e1633d6d6fe5ec2b86d2d8ba8437c5c4bcac0530b7d50f03af18dee28f7ebd8859d7063254f3751c1c3594a6146e430ea442489b8fb46dc38c34f42241b0783044b459ce8b377006bc7b1a3b58f41ad772ff65846f4946e9d68e1d78564f89b70b2c713c0e6efbb03100e317eb3214d9ed072fbee3a07";
        String privateKeyExp="1e4c5e9c4e403a97a3ee956c969c1b23efe43a379f46b33e867b67c59353b11e4c21422c41f96a0af360c7347198c2ff15ee59decf1c50116aae75bd716ef95a9dffd055bc872dc840a53f1d8fdbf08430efa394f8fe7ffc708ccbf4b9d46f6c833a415e57abd811d4b2b1aee64f59e1b87a74986fc7bd04514f924b5550a901";
        String publickeyMod = "83beb97d3aa44b696b2e1633d6d6fe5ec2b86d2d8ba8437c5c4bcac0530b7d50f03af18dee28f7ebd8859d7063254f3751c1c3594a6146e430ea442489b8fb46dc38c34f42241b0783044b459ce8b377006bc7b1a3b58f41ad772ff65846f4946e9d68e1d78564f89b70b2c713c0e6efbb03100e317eb3214d9ed072fbee3a07";
        String publicKeyExp="0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
        String privateKey = converHexStrPrivateKey2Base64(privateKeyMod,privateKeyExp);
        String publicKey = converHexStrPublicKey2Base64(publickeyMod,publicKeyExp);*/

        String data = "180804020130827jf1377599404256296028124NoticePay189800009399000199999999http://www.99ums.com/test/ordernotify/333测试";
        String signData = sign(data.getBytes(), privateKey);

        System.out.println("signData = "+ByteUtils.byteArray2HexString(Base64Utils.decode(signData)));
        System.out.println(verify(data.getBytes(), publicKey, signData));
    }

}
