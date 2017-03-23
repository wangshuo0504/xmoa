package com.zkxy.xmoa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtils {
    private final static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

    /**
     * <p><b>Description : <b/>十六进制 转换 byte[]</p>
     * @param
     *        hexStr
     */
    public static byte[] hexString2ByteArray(String hexStr) {
        if (hexStr == null) return null;
        hexStr = hexStr.replaceAll(" ", "");
        if (hexStr.length() % 2 != 0) {
            return null;
        }
        byte[] data = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++ ) {
            char hc = hexStr.charAt(2 * i);
            char lc = hexStr.charAt(2 * i + 1);
            byte hb = hexChar2Byte(hc);
            byte lb = hexChar2Byte(lc);
            if (hb < 0 || lb < 0) {
                return null;
            }
            int n = hb << 4;
            data[i] = (byte)(n + lb);
        }
        return data;
    }

    public static byte hexChar2Byte(char c) {
        if (c >= '0' && c <= '9') return (byte)(c - '0');
        if (c >= 'a' && c <= 'f') return (byte)(c - 'a' + 10);
        if (c >= 'A' && c <= 'F') return (byte)(c - 'A' + 10);
        return -1;
    }

    public static String byte2HexChar(byte b) {
        String tmp = Integer.toHexString(0xFF & b);
        if (tmp.length() < 2) tmp = "0" + tmp;
        return tmp;
    }

    /**
     * <p><b>Description : <b/>byte[] 转 16进制字符串 </p>
     * @param
     *        arr
     */
    public static String byteArray2HexString(byte[] arr) {
        StringBuilder sbd = new StringBuilder();
        for (byte b : arr) {
            String tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() < 2) tmp = "0" + tmp;
            sbd.append(tmp);
        }
        return sbd.toString();
    }

    /**
     * <p><b>Description : <b/>空格分隔的hex string</p>
     * 
     * @param arr
     * @return
     */
    public static String byteArray2HexStringWithSpace(byte[] arr) {
        StringBuilder sbd = new StringBuilder();
        for (byte b : arr) {
            String tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() < 2) tmp = "0" + tmp;
            sbd.append(tmp);
            sbd.append(" ");
        }
        return sbd.toString();
    }

    /**
     * <p><b>Description : <b/> 取start到end的 byte array，包含end。</p>
     * @param
     *      data 数据源
     * @param
     *      start 起始位置
     * @param
     *      end   结束位置
     */
    static public byte[] getData(byte[] data, int start, int end) {
        byte[] t = new byte[end - start + 1];
        System.arraycopy(data, start, t, 0, t.length);
        return t;
    }

    /**
     * <p><b>Description : <b/> 从data取start到end的数据，返回【bcd string】。end包含在取值范围。</p>
     * @param
     *      data 数据源
     * @param
     *      start 起始位置
     * @param
     *      end   结束位置
     */
    static public String getBCDString(byte[] data, int start, int end) {
        byte[] t = new byte[end - start + 1];
        System.arraycopy(data, start, t, 0, t.length);
        return ByteUtils.byteArray2HexString(t);
    }

    /**
     * <p><b>Description : <b/>从data取start到end的数据，返回 【hex string】。end包含在取值范围。</p>
     * @param
     *      data 数据源
     * @param
     *      start 起始位置
     * @param
     *      end   结束位置
     */
    static public String getHexString(byte[] data, int start, int end) {
        byte[] t = new byte[end - start + 1];
        System.arraycopy(data, start, t, 0, t.length);
        return ByteUtils.byteArray2HexStringWithSpace(t);
    }
    
    /**
     * <p><b>Description : <b/>根据issource，生成一个长度为4的byte数组 此数组记录isource </p>
     * @param
     *      isource
     */
    public static byte[] toByteArray(int isource) {
        return toByteArray(isource, 4);
    }

    /**
     * <p><b>Description : <b/> 根据issoirce，生成一个长度为len的字节数组 </p>
     * @param
     *        isource
     * @param
     *        len
     */
    public static byte[] toByteArray(int isource, int len) {
        byte[] bl = new byte[len];
        for (int i = 0; i < len; i++) {
            bl[i] = (byte) (isource >> 8 * i & 0xff);
        }
        return bl;
    }

    /**
     * <p><b>Description : <b/> 拼接两个字符数组</p>
     * @param
     *        a
     * @param
     *        b
     */
    public static byte[] revert(byte[] a, byte[] b) {
        if (a == null) {
            a = new byte[0];
        }
        if (b == null) {
            b = new byte[0];
        }
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    
    /**
     * <p><b>Description : <b/> 将byte数组转换成打印格式字符串，方便输出调试信息</p>
     * @param
     *      data
     * @param
     *      dataLen
     */
    public static String toPrintString(byte[] data, int dataLen) {
        if (data == null) return "";
        if (dataLen < 0) return "";
        int printLen = 0;
        if (dataLen > data.length)
            printLen = data.length;
        else
            printLen = dataLen;
        StringBuffer sb = new StringBuffer();
        String lenStr = int2HexString(data.length);
        int width = lenStr.length();
        String printStr = "";
        int loopLen = 0;
        loopLen = (printLen / 16 + 1) * 16;
        for (int i = 0; i < loopLen; i++ ) {
            if (i % 16 == 0) {
                sb.append("0x").append(formatHexStr(width, int2HexString(i))).append(": ");
                printStr = "";
            }
            if (i % 16 == 8) {
                sb.append(" ");
            }
            if (i < printLen) {
                sb.append(" ").append(formatHexStr(2, int2HexString(data[i])));
                if (data[i] > 31 && data[i] < 127)
                    printStr += (char)data[i];
                else
                    printStr += '.';
            } else {
                sb.append("   ");
            }
            if (i % 16 == 15) {
                sb.append(" ").append(printStr).append("\r\n");
            }
        }
        return sb.toString();
    }
    /**
     * <p><b>Description : <b/> int转HexString</p>
     * @param
     *         n 数字
     */
    private static String int2HexString(int n) {
        return Integer.toHexString(n).toUpperCase();
    }

    /**
     * <p><b>Description : <b/> 格式化Hex字符串的宽度，不足左补'0' </p>
     * @param
     *        width  指定长度
     * @param
     *        hexStr Hex字符串
     */
    private static String formatHexStr(int width, String hexStr) {
        if (hexStr.length() >= width) {
            return hexStr.substring(hexStr.length() - width);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < width - hexStr.length(); i++ ) {
            sb.append("0");
        }
        sb.append(hexStr);
        return sb.toString();
    }
}