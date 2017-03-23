package com.zkxy.xmoa.execl.poi;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

/**
 *<b>Description</b><br>
 * map 类型 导入 EXECL
 * @author DyLanM
 *
*/
public class MapExport extends BaseExportExcel{

	/**
	 *<b>Description</b><br>
	 * 实体对象 属性集合
	 */
	private List<String> keyList = new ArrayList<String>();
	/**
	 *<b>Description</b><br>
	 * sheet 列表头集合
	 */
	private List<MapHeader> map;
	
	/**
	 *<b>Description</b><br>
	 * 初始化
	 * @param map
	 */
	public MapExport(List<MapHeader> map){
		this.wb = new SXSSFWorkbook(500);
		this.styles = createStyles(wb);
		init(map);
	}
	
	/**
	 *<b>Description</b><br>
	 * 创建 sheet
	 * @param name  sheet 名称
	 * @param title 头标题
	 * @param lh  List<MapHeader> 列头
	 * @return
	 */
	public MapSheel createMapSheel(String name,String title,List<MapHeader> lh){
		return new MapSheel(this.wb, this.styles, name, title, lh);
	}
	
	/**
	 *<b>Description</b><br>
	 * 创建 sheet
	 * @param name sheet 名称
	 * @param title 头标题
	 * @return
	 */
	public MapSheel createMapSheel(String name,String title){
		return new MapSheel(this.wb, this.styles, name, title,this.map, this.headerList, this.width, this.keyList);
	}
	
	/**
	 *<b>Description</b><br>
	 * 构建 sheet，列表名称，宽度，实体属性
	 * @param lh
	 */
	private void init(List<MapHeader> lh){
		this.width = new int[lh.size()];
		for(int i=0 , length = lh.size() ; i < length ; i++ ){
			MapHeader mh= lh.get(i);
			this.headerList.add(mh.getTitle());
			this.keyList.add(mh.getMapKey());
			this.width[i] = mh.getWidth();
		}
	}

}
