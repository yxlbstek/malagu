package vip.malagu.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import vip.malagu.constants.PropertyConstant;

/**
 * 工具类
 * @see 1. 将 集合数据 转换成 树形结构数据
 * @see 2. 将 树形结构数据 转换成 平铺数据
 * @see 3. 收集 集合中 指定的 属性值
 * @see 4. 集合source转Map，Key为source元素的propertyName属性值，Value为该元素
 * @see 5. 判断请求是否来自手机端
 *
 */
public final class CommonUtils {
	
	private CommonUtils() {}
	
	private static final String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" }; 
	
	/**
	 * 将数据转换成 树形 结构
	 * @param datas 需转换的数据集合
	 * @param idProperty 集合中 对象的 主键 属性 (id)
	 * @param parentIdProperty 集合中 对象的 父级ID 属性 (parentId)
	 * @param childrenProperty 集合中 对象的 子集集合 属性(children)
	 * @return
	 */
	public static <T> List<T> listToTree(List<T> datas, String idProperty, String parentIdProperty, String childrenProperty) {
		List<T> result = new ArrayList<>();
		Map<Object, List<T>> childrenMap = new HashMap<>();
		
		for (T entity : datas) {
			Object entityId = BeanUtils.getFieldValue(entity, idProperty);
			Object entityParentId = BeanUtils.getFieldValue(entity, parentIdProperty);
			if (childrenMap.containsKey(entityId)) {
				BeanUtils.setFieldValue(entity, childrenProperty, childrenMap.get(entityId));
			} else {
				BeanUtils.setFieldValue(entity, childrenProperty, new ArrayList<T>());
				childrenMap.put(entityId, BeanUtils.getFieldValue(entity, childrenProperty));
			}

			if (entityParentId == null || "0".equals((String) entityParentId)) {
				result.add(entity);
			} else {
				List<T> children;
				if (childrenMap.containsKey(entityParentId)) {
					children = childrenMap.get(entityParentId);
				} else {
					children = new ArrayList<>();
					childrenMap.put(entityParentId, children);
				}
				children.add(entity);
			}
		}
		return result;
	}
	
	/**
	 * 将 树形结构 数据转换成 平铺数据
	 * @param datas 需要转换的数据集合
	 * @param childrenProperty 集合中 对象的 子集集合 属性(children)
	 * @return
	 */
	public static <T> List<T> treeToList(List<T> datas, String childrenProperty) {
		List<T> result = new ArrayList<>();
		analysisTree(datas, childrenProperty, result);
		return result;
	}

	private static <T> void analysisTree(List<T> datas, String childrenProperty, List<T> result) {
		if (!datas.isEmpty()) {
			for (T obj : datas) {
				result.add(obj);
				if (BeanUtils.getFieldValue(obj, childrenProperty) != null) {
					analysisTree(BeanUtils.getFieldValue(obj, childrenProperty), childrenProperty, result);
				}
			}
		}
	}

	/**
	 * 收集 集合中 指定的 属性值
	 * @param source 数据集合
	 * @param propertyName 属性名
	 * @param <T> 范型
	 * @return source集合每个对象的propertyName属性值的一个集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Set<T> collect(Collection<?> source, String propertyName) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.emptySet();
		}
		Set result = new HashSet(source.size());
		for (Object obj : source) {
			Object value = null;
			if (obj instanceof Map) {
				value = ((Map) obj).get(propertyName);
			} else {
				if (obj != null) {
					value = BeanUtils.getFieldValue(obj, propertyName);
				}
			}
			if (value != null) {
				result.add(value);
			}
		}
		return result;
	}

	/**
	 * 集合source转Map，Key为source元素的propertyName属性值，Value为该元素
	 * @param source 源集合
	 * @param propertyName 属性名
	 * @param <K> propertyName对应的属性的类型
	 * @param <V> source集合元素类型
	 * @return 索引Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, V> index(Collection<V> source, String propertyName) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.emptyMap();
		}
		Map result = new HashMap();

		for (Object obj : source) {
			Object value = getValue(obj, propertyName);
			result.put(value, obj);
		}
		return result;
	}
	
	/**
	 * 获取对象 obj 的指定属性值
	 * @param obj
	 * @param propertyName 属性
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getValue(Object obj, String property) {
		if (obj instanceof Map) {
			return ((Map) obj).get(property);
		} else if (obj != null) {
			return BeanUtils.getFieldValue(obj, property);
		}
		return null;
	}
	
	/**
	 * 判断请求是否来自手机端
	 * @param userAgent
	 * @return
	 */
	public static boolean checkAgentIsMobile(String userAgent) {
		if ((!userAgent.contains(PropertyConstant.WINDOWS_NT) || (userAgent.contains(PropertyConstant.WINDOWS_NT) && userAgent.contains("compatible; MSIE 9.0;"))) 
				&& !userAgent.contains(PropertyConstant.WINDOWS_NT) 
				&& !userAgent.contains("Macintosh")) {
			for (String item : agent) {
				if (userAgent.contains(item)) {
					return true;
				}
			}
		}
		return false;
	}

}
