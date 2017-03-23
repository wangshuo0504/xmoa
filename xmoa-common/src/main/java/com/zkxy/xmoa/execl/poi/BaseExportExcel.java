package com.zkxy.xmoa.execl.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *<b>Description</b><br>
 * Execl 导出 基础类 
 * @author DyLanM
 */
public abstract class BaseExportExcel {
	
	/**
	 *<b>Description</b><br>
	 *工作簿 
	 */
	protected SXSSFWorkbook wb;
	
	/**
	 *<b>Description</b><br>
	 * 列表头 文字
	 */
	protected List<String> headerList = new ArrayList<String>();
	
	/**
	 *<b>Description</b><br>
	 * 每个单元格的宽度
	 */
	protected int[] width;
	/**
	 *<b>Description</b><br>
	 * 样式
	 */
	protected Map<String, CellStyle> styles;
	
	public BaseExportExcel(){}
	
	/**
	 *<b>Description</b><br>
	 * 设置 基本的样式
	 * @param wb
	 * @return
	 */
	protected Map<String, CellStyle> createStyles(Workbook wb) {
		
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		//设置文字在单元格里面的位置 设置 上下
		style.setAlignment(CellStyle.ALIGN_CENTER);
		//设置文字在单元格里面的位置 左右
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		//设置 字体
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 指定单元格垂直居中对齐
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
        //style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
	/**
	 *<b>Description</b><br>
	 * 输出数据
	 * @param OutputStream 
	 * @throws IOException
	*/
	public void write(OutputStream os) throws IOException{
		wb.write(os);
	}
	
	/**
	 *<b>Description</b><br>
	 * 输出到客户端
	 * @param HttpServletResponse response 
	 * @param String fileName 文件名称
	 * @throws IOException
	*/
//	public void write(HttpServletResponse response, String fileName) throws IOException{
//		response.reset();
//        response.setContentType("application/octet-stream; charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
//		write(response.getOutputStream());
//	}
	

	/**
	 *<b>Description</b><br>
	 *  输出到文件
	 * @param  name String文件名称
	 * @throws IOException
	*/
	public void writeFile(String name) throws IOException{
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		os.close();
	}
	
	/**
	 *<b>Description</b><br>
	 *  清理临时文件
	*/
	public void dispose(){
		wb.dispose();
	}

}
