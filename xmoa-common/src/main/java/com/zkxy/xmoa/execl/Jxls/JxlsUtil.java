package com.zkxy.xmoa.execl.Jxls;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *<b>Description</b><br>
 * JXLS Execl 模板帮助类
 * 
 * 标签 用法  参考官网 : http://jxls.sourceforge.net/index.html
 * @author DyLanM
 */
public class JxlsUtil {

	private static XLSTransformer transformer = new XLSTransformer();
	
	/**
	 * <b>Description</b><br> 
     * 通过JXLS 导出excel 
     * 
     * @param templateFile - excel模版存放位置 如：  src/main/resources/template/new.xlsx   templateFile = "template/new.xlsx"
     * @param beans - 模版中填充的数据 
     * @param out - 生成模版输出流 
	 * @throws FileNotFoundException 
     */  
    public static void exportExcel(String templateFile,Map<String,Object> beans,OutputStream out) throws FileNotFoundException {  
      InputStream in= null;     
      try { 
    	    in = JxlsUtil.class.getClassLoader().getResourceAsStream(templateFile);  
            Workbook workbook = transformer.transformXLS(in,beans);  
            workbook.write(out); 
            out.flush();
      } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("导出excel错误!");  
      } finally {  
        	if (in != null){ try{in.close();}catch(IOException e){e.printStackTrace();} }  
            if (out != null){ try{out.close();}catch(IOException e){e.printStackTrace();} }  
      }  
      
    }

	/**
	 * <b>Description</b><br> 
     *  通过JXLS 导出excel
     *  
     * @param in excel 模板文件流
     * @param beans - 模版中填充的数据 
     * @param out - 生成模版输出流 
     */  
    public static void exportExcel(InputStream in,Map<String,Object> beans,OutputStream out){  

         try { 
            Workbook workbook = transformer.transformXLS(in,beans);  
            workbook.write(out); 
            out.flush();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("导出excel错误!");  
        } finally {  
        	if (in != null){ try{in.close();}catch(IOException e){e.printStackTrace();} }  
            if (out != null){ try{out.close();}catch(IOException e){e.printStackTrace();} }  
        }   
    }
    
	/**
	 * <b>Description</b><br> 
     *  通过JXLS 导出excel 多工作表
     *  
     * @param in excel 模板文件流
     * @param out - 生成模版输出流 
     * @param sheetNames - 每个sheet的名称
     * @param dataList  sheet工作表的数据  获取使用 ${datas.get("property")} datas 是固定的变量。
     * 
     */  
    public static void exportMultipleSheetsExcel(InputStream in,OutputStream out,List<String> sheetNames,List<Object> dataList){  

         try { 
        	Workbook workbook = transformer.transformMultipleSheetsList(in, dataList, sheetNames, "datas", new HashMap(), 0); 
            workbook.write(out); 
            out.flush();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("导出excel错误!");  
        } finally {  
        	if (in != null){ try{in.close();}catch(IOException e){e.printStackTrace();} }  
            if (out != null){ try{out.close();}catch(IOException e){e.printStackTrace();} }  
        }   
    }
    
	/**
	 * <b>Description</b><br> 
     *  通过JXLS 导出excel 多工作表
     *  
     * @param templateFile - excel模版存放位置 如：  src/main/resources/template/new.xlsx   templateFile = "template/new.xlsx"
     * @param out - 生成模版输出流 
     * @param sheetNames - 每个sheet的名称
     * @param dataList  sheet工作表的数据  获取使用 ${datas.get("property")} datas 是固定的变量。
     * 
     */  
    public static void exportMultipleSheetsExcel(String templateFile,OutputStream out,List<String> sheetNames,List<Object> dataList){  
    	InputStream in= null;
         try { 
        	in = JxlsUtil.class.getClassLoader().getResourceAsStream(templateFile);  
        	Workbook workbook = transformer.transformMultipleSheetsList(in, dataList, sheetNames, "datas", new HashMap(), 0); 
            workbook.write(out); 
            out.flush();
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("导出excel错误!");  
        } finally {  
        	if (in != null){ try{in.close();}catch(IOException e){e.printStackTrace();} }  
            if (out != null){ try{out.close();}catch(IOException e){e.printStackTrace();} }  
        }   
    }   
    
}
