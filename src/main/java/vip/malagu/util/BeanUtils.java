package vip.malagu.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean相关操作工具类
 * @author Lynn -- 2020年5月21日 下午5:10:36
 */
public final class BeanUtils {
	
	/**
	 * 获取类Clazz 的所有字段
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("The class must not be null");
		}
		List<Field> fieldList = new ArrayList<Field>();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				fieldList.add(field);
			}
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}
	
	/**
	 * 获取类Clazz 属性名为property 的字段
	 * @param clazz
	 * @param property 属性名
	 * @return
	 */
	public static Field getField(Class<?> clazz, String property) {
		String[] ps = property.split("\\.");
		Class<?> cls = clazz;
		Field field = null;
		for (int i = 0; i < ps.length; i++) {
			field = doGetField(cls, ps[i]);
			cls = field.getType();
		}

		return field;
	}
	
	private static Field doGetField(Class<?> clazz, String property) {
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(property)) {
					return field;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

	/**
	 * 获取对象bean 属性property 的值
	 * @param bean 对象
	 * @param property 属性
	 * @return
	 */
	public static <T> T getFieldValue(Object bean, String property) {
		Field field = getField(bean.getClass(), property);
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
			Field field = getField(bean.getClass(), property);
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
	
	/**
	 * 值转换
	 * @param type 类型
	 * @param value 值
	 * @return
	 */
	public static Object convertType(Class<?> type, String value) {
		Object result = null;
		String strType = type.getSimpleName();
		if (strType.equals("long")) {
			result = Long.parseLong(value);

		} else if (strType.equals("Long")) {
			result = Long.valueOf(value);

		} else if (strType.equals("int")) {
			result = Integer.parseInt(value);

		} else if (strType.equals("Integer")) {
			result = Integer.valueOf(value);

		} else if (strType.equals("float")) {
			result = Float.parseFloat(value);

		} else if (strType.equals("Float")) {
			result = Float.valueOf(value);

		} else if (strType.equals("double")) {
			result = Double.parseDouble(value);

		} else if (strType.equals("Double")) {
			result = Double.valueOf(value);

		} else if (strType.equals("BigDecimal")) {
			result = new BigDecimal(value);

		} else if (strType.equals("boolean") || strType.equals("Boolean")) {
			result = value.equals("0") || value.equals("true") ? true : false;

		} else if (strType.equals("Date")) {
			result = DateUtils.stringToDate(value, "yyyy-MM-dd HH:mm:ss");

		} else {
			result = value;

		}
		return result;
	}
	
}
