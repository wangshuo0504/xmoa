
package com.zkxy.xmoa.execl.poi;

import com.zkxy.xmoa.execl.poi.annotation.ExcelField;
import com.zkxy.xmoa.execl.poi.util.Reflections;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 *<b>Description</b><br>
 * 导入Excel文件 (支持"XLS”和“XLSX”格式）
 * @author DyLanM
 *
 */
public class ImportExcel {
	
	private static Logger log = LoggerFactory.getLogger(ImportExcel.class);
			
	/**
	 *<b>Description</b><br>
	 * 工作薄对象
	 */
	private Workbook wb;
	
	/**
	 *<b>Description</b><br>
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 *<b>Description</b><br>
	 * 标题行号
	 */
	private int headerNum;
		
	/**
	 *<b>Description</b><br>
	 * 导入Execl
	 * @param fileName 文件名称
	 * @param headerNum 标题行号，数据行标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, int headerNum) throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
	}
	

	/**
	 *<b>Description</b><br>
	 * 导入Execl
	 * @param file 文件对象
	 * @param headerNum 标题行号，数据行标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(File file, int headerNum) throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
	}


	/**
	 *<b>Description</b><br>
	 * 导入Execl 
	 * @param fileName 文件名称
	 * @param headerNum 标题行号，数据行标题行号+1
	 * @param sheetIndex  工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
	}
	
	/**
	 *<b>Description</b><br>
	 * 导入Execl
	 * @param file 导入文件对象
	 * @param headerNum 标题行号，数据行标题行号+1
	 * @param sheetIndex  工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(File file, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
	}
	
	/**
	 *<b>Description</b><br>
	 * 导入Execl
	 * @param MultipartFile 导入文件对象
	 * @param headerNum 标题行号，数据行标题行号+1
	 * @param sheetIndex  工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	//没这个包,不测试导入功能
//	public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex) 
//			throws InvalidFormatException, IOException {
//		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
//	}

	
	/**
	 *<b>Description</b><br>
	 * 初始化
	 * @param fileName 文件名称
	 * @param is
	 * @param headerNum 标题行号，数据行标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		if ( StringUtils.isBlank(fileName) ){
			throw new RuntimeException("导入文档为空!");
		}else if( fileName.toLowerCase().endsWith("xls") ){    
			this.wb = new HSSFWorkbook(is);    
        }else if(fileName.toLowerCase().endsWith("xlsx")){  
        	this.wb = new XSSFWorkbook(is);
        }else{  
        	throw new RuntimeException("文档格式不正确");
        }  
		if (this.wb.getNumberOfSheets()<sheetIndex){
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.wb.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
		log.debug("Initialize success.");
	}
	

	/**
	 *<b>Description</b><br>
	 * 获取行对象
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum){
		return this.sheet.getRow(rownum);
	}


	/**
	 *<b>Description</b><br>
	 * 获取数据行号
	 * @return
	 */
	public int getDataRowNum(){
		return headerNum + 1;
	}
	
	/**
	 *<b>Description</b><br>
	 * 获取最后数据行号
	 * @return
	 */
	public int getLastDataRowNum(){
		return this.sheet.getLastRowNum() + headerNum;
	}
	
	/**
	 *<b>Description</b><br>
	 * 获取最后列号
	 * @return
	 */
	public int getLastCellNum(){
		return this.getRow( headerNum ).getLastCellNum();
	}
	
	/**
	 *<b>Description</b><br>
	 * 获取单元格
	 * @param row 获取的行
	 * @param column 获取单元格列
	 * @return 
	 */
	public Object getCellValue(Row row, int column){
		Object val = "";
		try{
			Cell cell = row.getCell(column);
			if (cell != null){
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					val = cell.getNumericCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
					val = cell.getErrorCellValue();
				}
			}
		}catch (Exception e) {
			return val;
		}
		return val;
	}
	
	/**
	 *<b>Description</b><br>
	 * 获取导入数据列表
	 * @param cls 导入对象类型
	 * @param groups 导入分组
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException{
		List<Object[]> annotationList = new ArrayList<Object[]>();
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if ( groups != null && groups.length > 0 ){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == 2) ){
				if ( groups != null && groups.length > 0 ){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, m});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, m});
				}
			}
		}

		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		//log.debug("Import column count:"+annotationList.size());
		// Get excel data
		List<E> dataList = new ArrayList<E>();
		for (int i = this.getDataRowNum() , length = this.getLastDataRowNum() ; i < length ; i++) {
			E e = (E)cls.newInstance();
			int column = 0;
			Row row = this.getRow(i);
			//StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				Object val = this.getCellValue(row, column++);
				if (val != null){
					//ExcelField ef = (ExcelField)os[0];
					Class<?> valType = Class.class;
					if (os[1] instanceof Field){
						valType = ((Field)os[1]).getType();
					}else if (os[1] instanceof Method){
						Method method = ((Method)os[1]);
						if ("get".equals(method.getName().substring(0, 3))){
							valType = method.getReturnType();
						}else if("set".equals(method.getName().substring(0, 3))){
							valType = ((Method)os[1]).getParameterTypes()[0];
						}
					}
					//log.debug("Import value type: ["+i+","+colunm+"] " + valType);
					try {
						if (valType == String.class){
							val = String.valueOf(val.toString());
						}else if (valType == Integer.class){
							val = Double.valueOf(val.toString()).intValue();
						}else if (valType == Long.class){
							val = Double.valueOf(val.toString()).longValue();
						}else if (valType == Double.class){
							val = Double.valueOf(val.toString());
						}else if (valType == Float.class){
							val = Float.valueOf(val.toString());
						}else if (valType == Date.class){
							val = DateUtil.getJavaDate((Double)val);
						}else{
//							if (ef.fieldType() != Class.class){
//								val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
//							}else{
//								val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype."+valType.getSimpleName()+"Type")).getMethod("getValue", String.class).invoke(null, val.toString());
//							}
							break;
						}
					} catch (Exception ex) {
						log.info("获取cell值["+i+","+column+"] error: " + ex.toString());
						val = null;
					}
					//实体设值
					if (os[1] instanceof Field){
						Reflections.invokeSetter(e, ((Field)os[1]).getName(), val);
					}else if (os[1] instanceof Method){
						String mthodName = ((Method)os[1]).getName();
						if ("get".equals(mthodName.substring(0, 3))){
							mthodName = "set"+StringUtils.substringAfter(mthodName, "get");
						}
						Reflections.invokeMethod(e, mthodName, new Class[] {valType}, new Object[] {val});
					}
				}
				//sb.append(val+", ");
			}
			dataList.add(e);
			//log.debug("读取成功: ["+i+"] "+sb.toString());
		}
		return dataList;
	}

//	/**
//	 * 导入测试
//	 */
	public static void main(String[] args) throws Throwable {
		
		ImportExcel ei = new ImportExcel("D:\\人员信息8.xlsx", 1);
		
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				System.out.print(val+", ");
			}
			System.out.print("\n");
		}
		
	}

}
