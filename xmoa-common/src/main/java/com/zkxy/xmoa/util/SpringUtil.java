package com.zkxy.xmoa.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @Description 获取Spring 上下文工具类
 * @Copyright Copyright (c) 2009</p>
 * @Company 源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * @修改记录
 * @修改序号，修改日期，修改人，修改内容
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * <p><b>Description : <b/>根据Bean名称获取实例 </p>
	 * @param
	 *        name  Bean注册名称
	 * @return bean实例
	 * @throws BeansException
	 */
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * <p><b>Description : <b/>根据Bean class 获取实例 </p>
	 * @param
	 *        requiredType  Bean的class
	 * @return bean实例
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

}