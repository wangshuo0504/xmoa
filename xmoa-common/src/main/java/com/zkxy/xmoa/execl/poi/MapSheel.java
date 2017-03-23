package com.zkxy.xmoa.execl.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *<b>Description</b><br>
 * map 数据创建 sheet 填充数据
 * @author DyLanM
 *
*/
public class MapSheel extends BaseSheel{

	/**
	 *<b>Description</b><br>
	 * 列表头集合
	 */
	private List<MapHeader> map;
	/**
	 *<b>Description</b><br>
	 * 实体对象 属性集合
	 */
	private List<String> keyList;
	
	/**
	 *<b>Description</b><br>
	 * 创建Sheet 
	 * @param wb SXSSFWorkbook
	 * @param name  sheet 名称
	 * @param styles 样式
	 */
	protected MapSheel(SXSSFWorkbook wb, String name,Map<String, CellStyle> styles) {
		super(wb, name, styles);
	}
	
	/**
	 *<b>Description</b><br>
	 * 创建Sheet 并生成好标题 ，生成好 列表头
	 * @param wb SXSSFWorkbook
	 * @param styles
	 * @param name sheet 名称
	 * @param title sheet内标题
	 * @param lh List<MapHeader> 列头集合
	 */
	public MapSheel(SXSSFWorkbook wb,Map<String, CellStyle> styles,String name,String title,List<MapHeader> lh)
	{
		this(wb, name, styles);
		init(lh, title);
	}
	
	/**
	 *<b>Description</b><br>
	 *  创建Sheet 并生成好标题 ，生成好 列表头
	 * @param wb
	 * @param styles
	 * @param name
	 * @param title
	 * @param lh
	 * @param headerList
	 * @param width
	 * @param keyList
	 */
	public MapSheel(SXSSFWorkbook wb,Map<String, CellStyle> styles,String name,String title,List<MapHeader> lh,List<String> headerList,int[] width,List<String> keyList){
		this(wb, name, styles);
		this.keyList = keyList;
		this.map = lh;
		initialize(title, headerList, width);
	}
	
	/**
	 *<b>Description</b><br>
	 * 生产 单元格
	 * @param data
	 * @return
	 */
	public MapSheel addData(List<Map<String,Object>> data){
		for( int i = 0 , lengths = data.size() ; i < lengths ; i++ ){
			Row row = addRow();
			for( int j = 0 , length = keyList.size() ; j<length ; j++ ){
				addCell(row, j,data.get(i).get( keyList.get(j) ) );
			}
		}
		return this;
	}
	
	/**
	 *<b>Description</b><br>
	 * 创建 sheet 内容中的标题， 列表头
	 * @param lh
	 * @param title
	 */
	private void init(List<MapHeader> lh,String title){
		List<String> headerList = new ArrayList<String>();
		int[] width = new int[lh.size()];
		for(int i=0 ,length = lh.size() ; i < length ; i++ ){
			MapHeader mh= lh.get(i);
			headerList.add(mh.getTitle());
			keyList.add(mh.getMapKey());
			width[i] = mh.getWidth();
		}
		initialize(title, headerList, width);
	}
}
