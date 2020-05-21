package vip.malagu.util;

import java.lang.reflect.Field;

/**
 * Bean相关操作工具类
 * @author Lynn -- 2020年5月21日 下午5:10:36
 */
public final class BeanUtils {

	/**
	 * 获取对象bean 属性property 的值
	 * @param bean 对象
	 * @param property 属性
	 * @return
	 */
	public static <T> T getFieldValue(Object bean, String property) {
		Field field = FieldUtils.getField(bean.getClass(), property);
		return getFieldValue(bean, field);
	}
	
	/**
	 * 获取对象bean 字段field 的值
	 * @param bean 对象
	 * @param field 字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object bean, Field field) {
		T result = null;
		try {
			field.setAccessible(true);
			result = (T) field.get(bean);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * 设置对象bean 属性property 的值
	 * @param bean 对象
	 * @param property 属性
	 * @param value 值
	 */
	public static void setFieldValue(Object bean, String property, Object value) {
		try {
			Field field = FieldUtils.getField(bean.getClass(), property);
			field.setAccessible(true);
			field.set(bean, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 实例化
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
