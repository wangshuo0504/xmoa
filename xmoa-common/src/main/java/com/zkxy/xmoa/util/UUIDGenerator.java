package com.zkxy.xmoa.util;

import java.util.UUID;

/**
 * @Description  UUID 工具类
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
public class UUIDGenerator {

    private UUIDGenerator() {
    }

    /**
     * <p><b>Description : <b/>生产随机32位ID</p>
     */
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        return s.replaceAll("-","");
    }

    /**
     * <p><b>Description : <b/>生产随机32位ID</p>
     * @param
     *         number 指定随机32位ID 个数
     */
    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for( int i=0 ; i<number; i++ ){
            ss[i] = getUUID(); 
        } 
        return ss; 
    }

    /**
     * <p><b>Description : <b/>生产随机16位ID</p>
     */
    public static String getSerialId(){
    	 String s = UUID.randomUUID().toString(); 
    	 return s.substring(0,16);
    }
    
    public static void main(String args[]){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        System.out.println(s.length());
        System.out.println(s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24));
        System.out.println(s.replaceAll("-",""));
    }
}   