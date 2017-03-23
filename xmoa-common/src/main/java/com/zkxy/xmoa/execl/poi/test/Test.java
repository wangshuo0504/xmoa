package com.zkxy.xmoa.execl.poi.test;


import com.zkxy.xmoa.execl.poi.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class Test {

	public static void main(String[] arg) throws Exception{

		testBean();
		testMap();
		testImport();
	}
	
	/**
	 *<b>Description</b><br>
	 * 测试 对象数据导入
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void testBean() throws FileNotFoundException, IOException{
		List<MyBean> l = new ArrayList<MyBean>();
		for(int i=0;i<100;i++){
			l.add(new MyBean());
		}
		
		// 
		BeanExport be = ExportExcel.BeanExport(MyBean.class);
		be.createBeanSheet("1月份", "1月份人员信息").addData(l);
		be.createBeanSheet("2月份","2月份人员信息").addData(l);
		be.writeFile("E:/标准实体导出人员信息.xlsx");
		
		//通过分组 导出数据  知道导出 1，2组的数据
		BeanExport bes = ExportExcel.BeanExport(MyBean.class);
		bes.createBeanSheet("1月份", "1月份人员信息",MyBean.class,1,1,2).addData(l);
		bes.createBeanSheet("2月份","2月份人员信息",MyBean.class,1,1,2).addData(l);
		bes.writeFile("E:/实体分组导出人员信息.xlsx");
		
	}
	
	/**
	 *<b>Description</b><br>
	 *  测试 Map数据导入
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void testMap () throws FileNotFoundException, IOException{
		List<MapHeader> l  = new ArrayList<MapHeader>();
		l.add(new MapHeader("姓名","name",5000));
		l.add(new MapHeader("年龄","age",4000));
		l.add(new MapHeader("生日","birthdate",3000));
		l.add(new MapHeader("地址","address",5000));
		l.add(new MapHeader("双精度","d",4000));
		l.add(new MapHeader("float","f",6000));
		
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		for(int i=0;i<100;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name","闪电球");
			map.put("age",100);
			map.put("birthdate",new Date());
			map.put("address","北京市广东省AAA号123楼!");
			map.put("d",22.222d);
			map.put("f",295.22f);
			lm.add(map);
		}
	
		MapExport me  = ExportExcel.mapExport(l);
		me.createMapSheel("1月份","广东省人员信息").addData(lm);
		me.createMapSheel("2月份", "北京市人员信息").addData(lm);
		me.writeFile("E:/map导出人员信息.xlsx");
			
	}
	
	public static void testImport() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException{
		ImportExcel ei = new ImportExcel("E:\\标准实体导出人员信息.xlsx", 1);
//		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
//		Row row = ei.getRow(i);
//		  for (int j = 0; j < ei.getLastCellNum(); j++) {
//			Object val = ei.getCellValue(row, j);
//			System.out.print(val+", ");
//		  }
//		 System.out.print("\n");
//	    }
		List<MyBean> list = ei.getDataList(MyBean.class);
		for (MyBean myBean : list) {
			System.out.println( myBean.toString() );
		}
		
		
	}
}
