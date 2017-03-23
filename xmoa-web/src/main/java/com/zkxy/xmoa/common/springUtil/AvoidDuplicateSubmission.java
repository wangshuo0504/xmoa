/**
 * Created on 2015年6月29日 by Jack
 */
package com.zkxy.xmoa.common.springUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * 防止重复提交注解，用于方法上<br/>
 * 在新建页面方法上，设置needSaveToken()为true，此时拦截器会在Session中保存一个token， 同时需要在新建的页面中添加
 * <input type="hidden" name="token" value="${token}">
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:源本信息科技有限公司 Co., Ltd.
 * </p>
 * 
 * @author Caiming
 * @version 1.0 修改记录： 修改序号，修改日期，修改人，修改内容
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmission {
	boolean needSaveToken() default false;

	boolean needRemoveToken() default false;
}
