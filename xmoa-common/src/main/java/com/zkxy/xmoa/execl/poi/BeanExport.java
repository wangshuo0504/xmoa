package com.zkxy.xmoa.execl.poi;


import com.zkxy.xmoa.execl.poi.annotation.ExcelField;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;


/**
 *<b>Description</b><br>
 * 通过实体对象 生产 到 EXECL
 * @author DyLanM
 *
 */
public class BeanExport extends BaseExportExcel{
	private static Logger log = LoggerFactory.getLogger(BeanExport.class);
	/**
	 *<b>Description</b><br>
	 * 获取 实体对象的 属性字段
	 */
	private List<Object[]> annotationList = new ArrayList<Object[]>();
	
	/**
	 *<b>Description</b><br>
	 *
	 * @param cls
	 * @param type 1,导出数据,2导出模板
	 * @param groups
	 */
	public BeanExport(Class<?> cls, int type, int... groups){
		this.wb = new SXSSFWorkbook(500);
		this.styles = createStyles(wb);
		init(cls, type, groups);
		log.debug("创建成功");
	}
	
	/**
	 *<b>Description</b><br>
	 * 工作本默认的 class依据,如果调用createBeanSheet不传入class则使用此默认class
	 * @param cls
	 */
	public BeanExport(Class<?> cls){
		this(cls,1);
	}
	
	/**
	 *<b>Description</b><br>
	 * 
	 * @param name  sheet 工作表名称
	 * @param title 标题头
	 * @param headerList 列表头名称
	 * @param width 每列宽度
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,List<String> headerList,int[] width){
		return new BeanSheel(title, headerList, width, this.wb, name, this.styles);
	}
	
	/**
	 *<b>Description</b><br>
	 *
	 * @param name  sheet 工作表名称
	 * @param title 标题头
	 * @param headers 列表头名称
	 * @param width 每列宽度
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,String[] headers,int[] width){
		return new BeanSheel(title,Arrays.asList(headers), width, this.wb, name, this.styles);
	}
	
	/**
	 *<b>Description</b><br>
	 *
	 * @param name  sheet 工作表名称
	 * @param title 标题头
	 * @param cls
	 * @param type
	 * @param groups
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,Class<?> cls, int type,int... groups){
		return new BeanSheel(title, cls, type, this.wb, name, this.styles, groups);
	}
	
	/**
	 *<b>Description</b><br>
	 *
	 * @param name  sheet 工作表名称
	 * @param title 标题名称
	 * @param cls
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,Class<?> cls){
		return new BeanSheel(title, cls,1, this.wb, name, this.styles);
	}
	
	/**
	 *<b>Description</b><br>
	 * 创建 工作表，标题 ，表格列。
	 * @param name  sheet 工作表名称
	 * @param title 标题名称
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title){
		return new BeanSheel(this.annotationList,this.headerList, this.width, name, title, wb, styles);
	}
	
	/**
	 *<b>Description</b><br>
	 * 获取实体的 注解配置
	 * @param cls  实体对象
	 * @param type 1,导出数据,2导出模板
	 * @param groups
	 */
	private void init(Class<?> cls, int type, int... groups){
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && ( ef.type() == 0 || ef.type() == type) ){
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

		// 实体 属性排序
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		// 初始化 实体属性
		this.width = new int[annotationList.size()];
		for( int i=0 , length = annotationList.size() ; i < length ; i++ ){
			ExcelField ef = (ExcelField)annotationList.get(i)[0];
			String t = ef.title();
			// 如果是导出数据，则去掉注释
			if (type==1){
				String[] ss = StringUtils.split(t, "**", 2);
				if ( ss.length == 2 ){
					t = ss[0];
				}
			}
		    this.width[i] = ef.width();
		    //设置列表头
			this.headerList.add(t);
		}
	}
		
}
