package vip.malagu.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
@SuppressWarnings("hiding")
public final class RedisUtils {
	
	private static RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired  
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {  
		RedisUtils.redisTemplate = redisTemplate;  
    }
	
	/**
	 * 存入字符串
	 * @param key 键
	 * @param value 值
	 */
	public static void set(Object key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * 存入字符串 并 设置过期时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间
	 * @param unit 时间粒度
	 */
	public static void setAndTimeout(Object key, Object value, long time, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, time, unit);
	}
	
	/**
	 * 获取字符串
	 * @param key 键
	 * @return 
	 */
	public static Object get(Object key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 获取原字符串 并 重新赋值
	 * @param key 键
	 * @param value 新值
	 * @return 
	 */
	public static Object getAndSet(Object key, Object value) {
		return redisTemplate.opsForValue().getAndSet(key, value);
	}
	
	/**
	 * 在原字符串末尾 追加字符串
	 * @param key 键
	 * @param value 值
	 */
	public static void append(Object key, String value) {
		redisTemplate.opsForValue().append(key, value);
	}
	
	/**
	 * 存入对象
	 * @param key 键
	 * @param entity 对象
	 */
	public static <T> void setEntity(Object key, T entity) {
		redisTemplate.opsForValue().set(key, JSONObject.toJSONString(entity));
	}
	
	/**
	 * 存入对象  并 设置过期时间
	 * @param key 键
	 * @param entity 对象
	 * @param time 时间
	 * @param unit 时间粒度
	 */
	public static <T> void setEntityAndTimeout(Object key, T value, long time, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), time, unit);
	}
	
	/**
	 * 存入对象集合
	 * @param key 键
	 * @param value 集合对象
	 */
	public static <T> void setEntityList(Object key, List<T> entityList) {
		redisTemplate.opsForValue().set(key, JSONObject.toJSONString(entityList));
	}
	
	/**
	 * 存入对象集合 并 设置过期时间
	 * @param key 键
	 * @param entityList 对象集合
	 * @param time 时间
	 * @param unit 时间粒度
	 */
	public static <T> void setEntityListAndTimeout(Object key, List<T> entityList, long time, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, JSONObject.toJSONString(entityList), time, unit);
	}
	
	/**
	 * 获取对象
	 * @param key 键
	 * @return 
	 */
	public static <T> T getEntity(Object key, Class<T> clz) {
		Object obj = redisTemplate.opsForValue().get(key);
		if (obj != null) {
			return jsonToEntity((String) obj, clz);
		}
		return null;
	}
	
	/**
	 * 获取对象集合
	 * @param key 键
	 * @return 
	 */
	public static <T> List<T> getEntityList(Object key, Class<T> clz) {
		Object obj = redisTemplate.opsForValue().get(key);
		if (obj != null) {
			return jsonToList((String) obj, clz);
		}
		return null;
	}
	
	/**
	 * 获取对象 并 重新赋值
	 * @param key 键
	 * @param newEntity 新对象
	 * @return 
	 */
	public static <T> T getEntityAndSet(Object key, T newEntity, Class<T> clz) {
		Object obj = redisTemplate.opsForValue().getAndSet(key, JSONObject.toJSONString(newEntity));
		if (obj != null) {
			return jsonToEntity((String) obj, clz);
		}
		return null;
	}
	
	/**
	 * 获取对象集合 并 重新赋值
	 * @param key 键
	 * @param newEntityList 新对象集合
	 * @return 
	 */
	public static <T> List<T> getEntityListAndSet(Object key, List<T> newEntityList, Class<T> clz) {
		Object obj = redisTemplate.opsForValue().getAndSet(key, JSONObject.toJSONString(newEntityList));
		if (obj != null) {
			return jsonToList((String) obj, clz);
		}
		return null;
	}
	
	/**
	 * 转换成 实体类对象
	 * @param text JSON字符串
	 * @param clz 对象的类型
	 * @return
	 */
	public static <T> T jsonToEntity(String text, Class<T> clz) {
		return JSONArray.parseObject(text, clz);
	}
	
	/**
	 * 转换成 实体类对象集合
	 * @param text JSON字符串
	 * @param clz 对象的类型
	 * @return
	 */
	public static <T> List<T> jsonToList(String text, Class<T> clz) {
		return JSONArray.parseArray(text, clz);
	}
	
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return
	 */
	public static Boolean exist(Object key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 移除缓存数据
	 * @param key 键
	 */
	public static void delete(Object key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * 存入Map
	 * @param key 键
	 * @param hashKey Map的key
	 * @param value Map的value
	 */
	public static void put(Object key, Object hashKey, Object hashValue) {
		redisTemplate.opsForHash().put(key, hashKey, hashValue);;
	}
	
	/**
	 * 存入Map
	 * @param key 键
	 * @param map
	 */
	public static void putAll(Object key, Map<? extends Object, ? extends Object> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	/**
	 * 获取Map
	 * @param key 键
	 */
	public static Map<? extends Object, ? extends Object> getMap(Object key) {
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * 获取Map中指定的值
	 * @param key 键
	 * @param hashKey Map的key
	 * @return
	 */
	public static Object getMapValue(Object key, Object hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	
	/**
	 * 获取Map中所有的key
	 * @param key 键
	 * @return
	 */
	public static Set<Object> getMapKeys(Object key) {
		return redisTemplate.opsForHash().keys(key);
	}
	
	/**
	 * 获取Map中所有的值
	 * @param key 键
	 * @return
	 */
	public static List<Object> getMapValues(Object key) {
		return redisTemplate.opsForHash().values(key);
	}
	
	/**
	 * 获取Map的长度
	 * @param key 键
	 * @return
	 */
	public static long getMapSize(Object key) {
		return redisTemplate.opsForHash().size(key);
	}
	
	/**
	 * 判断Map中key是否存在
	 * @param key 键
	 * @param hashKey Map的key
	 * @return
	 */
	public static Boolean existMapKey(Object key, Object hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	/**
	 * 移除Map中指定的key
	 * @param key 键
	 * @param hashKey Map的key
	 */
	public static void deleteMapKey(Object key, Object hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);
	}
	
	/**
	 * 获取自增数字,可用于自增ID的生成
	 * @param key 可当做数据库 表 的分类标识
	 * @param delta 递增的基数
	 * @return
	 */
	public static long getOnlyNumber(String key, long delta) {
		try {
			RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
			return counter.addAndGet(delta);
		} catch (Exception e) {
			return IdUtils.getOnlyNumberId();
		}
	}

}
