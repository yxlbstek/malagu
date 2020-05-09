package vip.malagu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

@SuppressWarnings("hiding")
public final class TreeUtils {

	/**
	 ** 将数据转换成 树形 结构
	 * @param datas 需转换的数据集合
	 * @param idProperty 集合中 对象的 主键 属性 (id)
	 * @param parentIdProperty 集合中 对象的 父级ID 属性 (parentId)
	 * @param childrenProperty 集合中 对象的 子集集合 属性(children)
	 * @return
	 */
	public static <T> List<T> buildTree(List<T> datas, String idProperty, String parentIdProperty, String childrenProperty) {
		List<T> result = new ArrayList<T>();
		Map<Object, List<T>> childrenMap = new HashMap<Object, List<T>>();
		
		for (T entity : datas) {
			Object entityId = BeanUtils.getFieldValue(entity, idProperty);
			Object entityParentId = BeanUtils.getFieldValue(entity, parentIdProperty);
			if (childrenMap.containsKey(entityId)) {
				BeanUtils.setFieldValue(entity, childrenProperty, childrenMap.get(entityId));
			} else {
				BeanUtils.setFieldValue(entity, childrenProperty, new ArrayList<T>());
				childrenMap.put(entityId, BeanUtils.getFieldValue(entity, childrenProperty));
			}

			if (entityParentId == null || (entityParentId != null && entityParentId.toString().equals("0"))) {
				result.add(entity);
			} else {
				List<T> children;
				if (childrenMap.containsKey(entityParentId)) {
					children = childrenMap.get(entityParentId);
				} else {
					children = new ArrayList<T>();
					childrenMap.put(entityParentId, children);
				}
				children.add(entity);
			}
		}
		return result;
	}
	
	/**
	 * * 将 树形结构 数据转换成 平铺数据
	 * @param datas 需要转换的数据集合
	 * @param childrenProperty 集合中 对象的 子集集合 属性(children)
	 * @return
	 */
	public static <T> List<T> cancelTree(List<T> datas, String childrenProperty) {
		List<T> result = new ArrayList<T>();
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

}
