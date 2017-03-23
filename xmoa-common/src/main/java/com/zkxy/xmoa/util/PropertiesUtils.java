package com.zkxy.xmoa.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by zm on 2016/11/10.
 */
public class PropertiesUtils {
    /**
     * 获取当前环境config/config_xx.properties文件指定key的属性值
     * @param key
     * @return
     */
    public static String getConfigString(String key) {
         String runEnv = Tools.getRunEnv();
         String bundleName = "config/config_"+runEnv;
         return getString(bundleName,key);
    }

    /**
     * 获取bundleName的指定key的值
     * @param bundleName
     * @param key
     * @return
     */
    public static String getString(String bundleName, String key) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "";
        }
    }

    /**
     * 获取指定properties文件的内容
     * @param fileName
     * @return
     */
    public static Properties getPropertyFileContent(String fileName){
        Properties prop = new Properties();
        try {
            prop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
