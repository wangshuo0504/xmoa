/**
 * Created on 2015年10月9日 by ZhouMin
 */
package com.zkxy.xmoa.util;




import com.zkxy.xmoa.common.exception.BusinessException;

import java.io.*;


/**
 * @Description  文件工具类
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class FileUtil {
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * <p><b>Description : <b/>  将文件编码为BASE64字符串  大文件慎用，可能会导致内存溢出 </p>
     * 
     * @param filePath
     *            文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath)
        throws Exception {
        byte[] bytes = fileToByte(filePath);
        return Base64Utils.encode(bytes);
    }

    /**
     * <p><b>Description : <b/> BASE64字符串转回文件 </p>
     * 
     * @param filePath
     *            文件绝对路径
     * @param base64
     *            编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64)
        throws Exception {
        byte[] bytes = Base64Utils.decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * <p><b>Description : <b/> 文件转换为二进制数组 </p>
     * 
     * @param filePath
     *            文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath)
        throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }

    /**
     * <p><b>Description : <b/> 二进制数据写文件 </p>
     * 
     * @param bytes
     *            二进制数据
     * @param filePath
     *            文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath)
        throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        if(!destFile.createNewFile()){
            throw new BusinessException("创建文件失败:" + filePath);
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(destFile);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
        } finally {
            if (out != null) {
                out.close();
            }
            in.close();
        }

    }

    /**
     *<p><b>Description : <b/> 文件保存</p>
     * @param
     *       path 文件路径
     * @param
     *       fileName  文件名称
     * @param
     *       input
     */
    public static void fileSave(String path, String fileName, InputStream input) {
        OutputStream output = null;
        int length = 0;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            output = new FileOutputStream(file);
            byte[] buffer = new byte[CACHE_SIZE];
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (Exception e) {} finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {}
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {}
        }
    }
}
