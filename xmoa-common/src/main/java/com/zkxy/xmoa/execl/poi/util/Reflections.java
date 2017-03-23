
package com.zkxy.xmoa.execl.poi.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

/**
 *<b>Description</b><br>
 *
 * 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 * @author DyLanM
 *
 */
public class Reflections {
	
	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static Logger logger =  LoggerFactory.getLogger(Reflections.class);


	/**
	 *<b>Description</b><br>
	 * 调用Getter方法.
	 * @param obj  实体对象
	 * @param propertyName 属性名称
	 * @return 
	 */
	public static Object invokeGetter(Object obj, String propertyName) {
		String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 *<b>Description</b><br>
	 * 调用Setter方法, 仅匹配方法名
	 * @param obj
	 * @param propertyName
	 * @param value
	 */
	public static void invokeSetter(Object obj, String propertyName, Object value) {
		String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
		invokeMethodByName(obj, setterMethodName, new Object[] { value });
	}

	/**
	 *<b>Description</b><br>
	 * 直接读取对象属，无视private/protected修饰，不经过getter函数.
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 *<b>Description</b><br>
	 * 直接设置对象属，无视private/protected修饰，不经过setter函数.
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error( e.getMessage(),e);
		}
	}

	/**
	 *<b>Description</b><br>
	 * 直接调用对象方法, 无视private/protected修饰。
	 * 用于性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用。
	 * 同时匹配方法和参数类型
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 *<b>Description</b><br>
	 * 直接调用对象方法, 无视private/protected修饰符，
	 * 用于性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用。
	 * 只匹配函数名，如果有多个同名函数调用第一个。
	 * @param obj
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 *<b>Description</b><br>
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问。
	 * 如向上转型到Object仍无法找则返回null.
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		if( obj == null || !StringUtils.isNotBlank(fieldName) )
           return null;
		
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义，继续向上转型
			}
		}
		return null;
	}

	/**
	 *<b>Description</b><br>
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问。
	 * 如向上转型到Object仍无法找则返回null.
	 * 匹配函数参数类型 
	 * 用于方法被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,final Class<?>... parameterTypes) {
		if( obj == null || !StringUtils.isNotBlank(methodName) )
	        return null;

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定继续向上转型
			}
		}
		return null;
	}

	/**
	 *<b>Description</b><br>
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问。
	 * 如向上转型到Object仍无法找则返回null.
	 * 用于方法被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 * 只匹配函数名。
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
		if( obj == null || !StringUtils.isNotBlank(methodName) )
	        return null;

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 *<b>Description</b><br>
	 * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱
	 * @param method
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 *<b>Description</b><br>
	 * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱
	 * @param field
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
				.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 *<b>Description</b><br>
	 * 通过反射, 获得Class定义中声明的泛型参数的类，注意泛型必须定义在父类处。
	 * 如无法找到，返回Object.class.
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}


	/**
	 *<b>Description</b><br>
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到，返回Object.class.
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	public static Class<?> getUserClass(Object instance) {
	    //Assert.notNull(instance, "Instance must not be null");
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}


	/**
	 *<b>Description</b><br>
	 * 将反射时的checked exception转换为unchecked exception.
	 * @param e
	 * @return
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}
