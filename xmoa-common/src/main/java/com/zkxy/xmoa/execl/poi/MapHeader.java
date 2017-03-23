package com.zkxy.xmoa.execl.poi;

/**
 *<b>Description</b><br>
 *  sheet 列表头
 * @author DyLanM
 */
public class MapHeader {
	/**
	 *<b>Description</b><br>
	 * 列名称
	 */
	private String title;
	/**
	 *<b>Description</b><br>
	 * 属性 key
	 */
	private String mapKey;
	/**
	 *<b>Description</b><br>
	 * 宽度
	 */
	private int width = 3000;
	
	public MapHeader(String title, String mapKey, int width) {
		this.title = title;
		this.mapKey = mapKey;
		this.width = width;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
