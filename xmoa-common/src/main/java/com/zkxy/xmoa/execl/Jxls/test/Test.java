package com.zkxy.xmoa.execl.Jxls.test;



import com.zkxy.xmoa.execl.Jxls.JxlsUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		//test();
		testMultipleSheets();
	}
	
	/**
	 * <b>Description</b><br>
	 *  单个 sheet 操作
	 * @throws FileNotFoundException
	 */
	private static void test() throws FileNotFoundException {
		
		OutputStream os = new FileOutputStream("E:\\new.xlsx");  
   
        Map<String, Object> beans = new HashMap<String, Object>();  
          
        List<Map<String,String>> fruitList = new ArrayList<Map<String,String>>();  
          
        Map<String,String> fruit = null;  
        fruit = new HashMap<String, String>();  
        fruit.put("name", "苹果");  
        fruit.put("price", "100");  
        fruitList.add(fruit);  
          
        fruit = new HashMap<String, String>();  
        fruit.put("name", "香蕉");  
        fruit.put("price", "200");  
        fruitList.add(fruit);  
          
        beans.put("fruits",fruitList);  
        JxlsUtil.exportExcel("com/zkxy/xmoa/execl/Jxls/test/temp.xlsx", beans, os);
	} 
	
	private static void testMultipleSheets() throws FileNotFoundException {
	
		OutputStream out = new FileOutputStream("E:\\newMultipleSheets.xlsx");
	
		
		
    	List sheetNames = new ArrayList();
        List dataList = new ArrayList();
        for(int i = 1; i <= 10 ; i++ ){
        	
            Map<String, Object> beans = new HashMap<String, Object>();  
            
            List<Map<String,String>> fruitList = new ArrayList<Map<String,String>>();  
              
            Map<String,String> fruit = null;  
            fruit = new HashMap<String, String>();  
            fruit.put("name", "苹果"+i);  
            fruit.put("price", "100");  
            fruitList.add(fruit);  
              
            fruit = new HashMap<String, String>();  
            fruit.put("name", "香蕉"+ i);  
            fruit.put("price", "200");  
            fruitList.add(fruit);  
              
            beans.put("fruits",fruitList); 
        	
        	dataList.add(beans);
        	
            sheetNames.add( " 第 "+ i +"页");
        }
        
        JxlsUtil.exportMultipleSheetsExcel("com/zkxy/xmoa/execl/Jxls/test/temp1.xlsx", out, sheetNames, dataList);
	}


}
