package com.zkxy.xmoa.execl.poi;


import com.zkxy.xmoa.execl.poi.annotation.ExcelField;
import com.zkxy.xmoa.execl.poi.util.Reflections;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;


/**
 *<b>Description</b><br>
 * 将实体数据 填充到EXECL中
 * @author DyLanM
 *
 */
public  class BeanSheel extends BaseSheel{
	private static Logger log = LoggerFactory.getLogger(BeanSheel.class);
		
	List<Object[]> annotationList = new ArrayList<Object[]>();

	/**
	 *<b>Description</b><br>
	 * 构建 sheet
	 * @param title 表格标题，传“空值，表示无标题
	 * @param headerList 表头列表
	 * @return 
	 */
	public BeanSheel(String title, List<String> headerList,int[] width,SXSSFWorkbook wb,String name,Map<String, CellStyle> styles) {
		super(wb, name, styles);
		initialize(title, headerList,width);
	}
	
	/**
	 *<b>Description</b><br>
	 * 构建 sheet
	 * @param title 表格标题，传“空值，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 * @param type 导出类型 1:导出数据 2:导出模板）
	 * @param groups 导入分组
	 * @return 
	 */
	public BeanSheel(String title, Class<?> cls, int type,SXSSFWorkbook wb,String name,Map<String, CellStyle> styles ,int... groups) {
		super(wb, name, styles);
		init(title, cls, type, groups);
	}
	
	public BeanSheel(List<Object[]> annotationList,List<String> headerList,int[] width,String name,String title,SXSSFWorkbook wb,Map<String, CellStyle> styles) {
		super(wb, name, styles);
		this.annotationList = annotationList;
		initialize(title, headerList,width);
	}
	
	/**
	 *<b>Description</b><br>
	 * 加载实体属性
	 * @param title 表格标题，传“空值，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 * @param type 导出类型: 1:导出数据2：导出模板）
	 * @param groups 导入分组
	 * @return 
	 */
	private void init(String title, Class<?> cls, int type, int... groups){
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
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

		//实体属性排序 根据注解 sort
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		//获取注解信息
		List<String> headerList = new ArrayList<String>();
		int[] width = new int[annotationList.size()];
		for(int i=0;i<annotationList.size();i++){
			ExcelField ef = (ExcelField)annotationList.get(i)[0];
			String t = ef.title();
			// 如果是导出数据，则去掉注释
			if ( type == 1 ){
				String[] ss = StringUtils.split(t, "**", 2);
				if ( ss.length == 2 ){
					t = ss[0];
				}
			}
		    width[i]=ef.width();
			headerList.add(t);
		}
		
		initialize(title, headerList,width);
	}
	
	/**
	 *<b>Description</b><br>
	 * 填充 数据
	 * @param list<T>
	 * @return
	 */
	public <E> BeanSheel addData(List<E> list){
		for (E e : list){
			
			int colunm = 0;
			Row row = this.addRow();
			//StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				ExcelField ef = (ExcelField)os[0];
				Object val = null;
				try{
					if (StringUtils.isNotBlank(ef.value())){
						val = Reflections.invokeGetter(e, ef.value());
					}else{
						if (os[1] instanceof Field){
							val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
						}
					}
				}catch(Exception ex) {
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				//sb.append(val + ", ");
			}
			//log.debug("成功写入: ["+row.getRowNum()+"] "+sb.toString());
		}
		return this;
	}
	
}