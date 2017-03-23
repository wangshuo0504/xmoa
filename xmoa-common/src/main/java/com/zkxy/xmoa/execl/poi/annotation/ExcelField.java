/**
 *
 */
package com.zkxy.xmoa.execl.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *<b>Description</b><br>
 * Excel注解定义
 * @author DyLanM
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 *<b>Description</b><br>
	 * 导出字段名（默认调用当前字段的”get”方法，如指定导出字段为对象，请填写“对象名.对象属?”，例：“area.name” “office.name”）
	 * @return
	 */
	String value() default "";
	
	/**
	 *<b>Description</b><br>
	 * 导出字段标题（需要添加批注请用“**”分隔，标题**批注，仅对导出模板有效）
	 * @return
	 */
	String title();
	
	/**
	 *<b>Description</b><br>
	 * 字段类型  0: 导出导入; 1: 仅导出 2: 仅导入
	 * @return
	 */
	int type() default 0;

	/**
	 *<b>Description</b><br>
	 * 导出字段对齐方式:自动；1：靠左；2：居中；3：靠右）
	 * @return
	 */
	int align() default 0;
	
	/**
	 *<b>Description</b><br>
	 * 单元格宽
	 * @return
	 */
	int width() default 3000;
	
	/**
	 *<b>Description</b><br>
	 * 导出字段字段排序（升序）
	 * @return
	 */
	int sort() default 0;

	/**
	 *<b>Description</b><br>
	 *  反射对象 类型
	 * @return
	 */
	Class<?> fieldType() default Class.class;
	
	/**
	 *<b>Description</b><br>
	 * 字段归属组（根据分组导出导入)
	 * @return
	 */
	int[] groups() default {};
}
