package com.zkxy.xmoa.execl.poi;

import java.util.List;

/**
 *<b>Description</b><br>
 * 导出Execl 创建 SXSSFWorkbook
 * @author DyLanM
 *
 */
public class ExportExcel {
	
	/**
	 *<b>Description</b><br>
	 * 通过实体生成 Excel
	 * @param cls 实体对象
	 * @return
	 */
	public static BeanExport BeanExport(Class<?> cls){
		return new BeanExport(cls);
	}
	
	/**
	 *<b>Description</b><br>
	 * 通过实体生成  Excel
	 * @param cls 实体对象
	 * @param type 1,导出数据,2导出模板
	 * @param groups
	 * @return
	 */
	public static BeanExport BeanExport(Class<?> cls, int type, int... groups){
		return new BeanExport(cls, type, groups);
	}
	
	/**
	 *<b>Description</b><br>
	 * 自定义列表头 导出Execl
	 * @param map List<MapHeader> sheet 列表头
	 * @return
	 */
	public static MapExport mapExport(List<MapHeader> map){
		return new MapExport(map);
	}

}
