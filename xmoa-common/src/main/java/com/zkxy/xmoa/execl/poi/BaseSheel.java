package com.zkxy.xmoa.execl.poi;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *<b>Description</b><br>
 *  工 作 表 公共方法
 * @author DyLanM
 */
public abstract class BaseSheel {
	protected static Logger log = LoggerFactory.getLogger(BaseSheel.class);
	/**
	 *<b>Description</b><br>
	 * 工作表 
	 */
	protected Sheet sheet;
	protected SXSSFWorkbook wb;
	protected Map<String, CellStyle> styles;
	/**
	 *<b>Description</b><br>
	 * 第几行
	 */
	protected int rownum = 0;
	
	/**
	 *<b>Description</b><br>
	 * 创建 Sheeet
	 * @param wb  SXSSFWorkbook
	 * @param name sheet 名称
	 * @param styles 样式集合
	 */
	protected BaseSheel(SXSSFWorkbook wb,String name,Map<String, CellStyle> styles) {
		this.sheet = wb.createSheet(name);
		this.styles = styles;
		this.wb = wb;
	}
	
	/**
	 *<b>Description</b><br>
	 * 创建sheet 标题，创建 列表头
	 * @param title  标题
	 * @param headerList  列表头
	 * @param width
	 */
	protected void initialize(String title, List<String> headerList,int[] width) {

		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle( styles.get("title") );
			titleCell.setCellValue(title);
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
		}
		// Create header
		if (headerList == null){
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow( rownum++ );
		headerRow.setHeightInPoints(16);
		for (int i = 0, length = headerList.size() ; i < length ; i++) {
			Cell cell = headerRow.createCell(i);//创建列表头的单元格
			cell.setCellStyle( styles.get("header") );//设置单元格样式
			
			String[] ss = StringUtils.split( headerList.get(i), "**", 2 );
			
			if (ss.length==2){
				//如果有注释，则加上注释
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(headerList.get(i));
			}
			//自动调整宽度
			sheet.autoSizeColumn(i);
		}
		if( width != null ){
			for (int i = 0,length =  width.length ; i < length; i++ ) {  
			   sheet.setColumnWidth( i, width[i] < 3000 ? 3000 : width[i] );  
			}
		}
		
		log.debug("Initialize success.");
	}
	
	/**
	 *<b>Description</b><br>
	 * 添加 行
	 * @return Row
	 */
	public Row addRow(){
		return sheet.createRow( rownum++ );
	}
	
	/**
	 *<b>Description</b><br>
	 * 添加单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val){
		return this.addCell(row, column, val, 1, Class.class);
	}
	
	/**
	 *<b>Description</b><br>
	 * 添加单元格 并设置值
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 设置的值
	 * @param align  对齐方式: 1:靠左;  2:居中; 3:靠右
	 * @param fieldType
	 * @return 单元格对象 Cell
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType){
		Cell cell = row.createCell(column);
		DecimalFormat df = new DecimalFormat("#.00");
	//	CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {		
				cell.setCellValue(df.format(val));
			} else if (val instanceof Float) {
				cell.setCellValue(df.format(val));
			} else if (val instanceof Date) {
				CellStyle	style =styles.get("data"+( align >= 1 && align <= 3 ? align : "" ));
				DataFormat format = wb.createDataFormat();
	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue( (String)fieldType.getMethod("setValue", Object.class).invoke(null, val) );
				}else{
					cell.setCellValue( (String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val) );
				}
			}
		} catch (Exception ex) {
			log.info("设置cell["+row.getRowNum()+","+column+"] 错误: " + ex.toString());
			cell.setCellValue(val.toString());
		}

		return cell;
	}
	
}
