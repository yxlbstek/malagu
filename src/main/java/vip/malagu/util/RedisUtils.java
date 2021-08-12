package vip.malagu.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.T;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import vip.malagu.custom.exception.CustomException;
import vip.malagu.enums.SystemErrorEnum;

/**
 * Redis操作工具类
 * @author Lynn -- 2020年5月21日 下午5:13:59
 */
@Component
@SuppressWarnings("hiding")
public final class RedisUtils {
	
	private RedisUtils() {}
	
	private static RedisTemplate<Object, Object> redisTemplate;
	
	private static RedissonClient redissonClient;
	
    private static String host;
 
    private static String password;
 
    private static Integer port;
    
    private static int databaseIndex;
    
    @Value("${redis.host}")
	public void setHost(String host) {
    	RedisUtils.host = host;
	}

    @Value("${redis.password}")
	public void setPassword(String password) {
    	RedisUtils.password = password;
	}

    @Value("${redis.port}")
	public void setPort(Integer port) {
    	RedisUtils.port = port;
	}

    @Value("${redis.databaseIndex:0}")
	public void setDatabaseIndex(int databaseIndex) {
    	RedisUtils.databaseIndex = databaseIndex;
	}

	@Autowired  
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {  
		RedisUtils.redisTemplate = redisTemplate;  
    }

	//锁
	private static RedissonClient redissonClient() {
		if (redissonClient == null) {
			Config config = new Config();
			config.useSingleServer()
					.setAddress(String.format("redis://%s:%s", host, port))
					.setDatabase(databaseIndex)
					.setPassword(password);
			redissonClient = Redisson.create(config);
		}
		return redissonClient;
	}

	/**
	 * 存入字符串
	 * @param key 键
	 * @param value 值
	 */
	public static void set(Object key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量存入字符串
	 * @param map 键-值
	 */
	public static void setAll(Map<Object, Object> map) {
		try {
			redisTemplate.opsForValue().multiSet(map);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入字符串 并 设置过期时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间
	 * @param unit 时间粒度
	 */
	public static void setAndTimeout(Object key, Object value, long time, TimeUnit unit) {
		try {
			redisTemplate.opsForValue().set(key, value, time, unit);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量存入 map
	 * @param map
	 * @return 
	 */
	public static void setByPipelined(Map<String, String> map) {
		try {
			redisTemplate.executePipelined(new RedisCallback<Object>() {
	           
				@Override
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
					for (Entry<String, String> entry : map.entrySet()) {
						connection.set(entry.getKey().getBytes(), entry.getValue().getBytes());
					}
	                return null;
	            }
				
	        });
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量存入-过期 map
	 * @param map
	 * @return 
	 */
	public static void setByPipelinedAndTimeout(Map<String, String> map, long time, TimeUnit unit) {
		try {
			redisTemplate.executePipelined(new RedisCallback<Object>() {
	           
				@Override
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
					for (Entry<String, String> entry : map.entrySet()) {
						connection.set(entry.getKey().getBytes(), entry.getValue().getBytes(), Expiration.from(time, unit), SetOption.UPSERT);
					}
	                return null;
	            }
				
	        });
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取所有的key
	 */
	public static Set<Object> keys() {
		try {
			return redisTemplate.keys("*");
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 修改key
	 * @param oldKey
	 * @param newKey
	 */
	public static void renameKey(Object oldKey, Object newKey) {
		try {
			redisTemplate.rename(oldKey, newKey);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 判断key的类型
	 * @param key
	 * @return
	 */
	public static String type(String key) {
		try {
			DataType dataType = redisTemplate.type(key);
			return dataType.code();
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取字符串
	 * @param key 键
	 * @return 
	 */
	public static Object get(Object key) {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量获取字符串
	 * @param keys 键
	 * @return 
	 */
	public static List<Object> multiGet(List<Object> keys) {
		try {
			return redisTemplate.opsForValue().multiGet(keys);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量获取
	 * @param keys 键
	 * @return 
	 */
	public static List<Object> getByPipelined(List<Object> keys) {
		try {
			return redisTemplate.executePipelined(new RedisCallback<String>() {
	            @Override
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
	            	for (int i = 0; i < keys.size(); i++) {
	            		connection.get(keys.get(i).toString().getBytes());
	            	}
	                return null;
	            }
	        });
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量获取 Map
	 * @param key 键
	 * @param hashKeys 
	 * @return 
	 */
	public static List<Object> getMapValuesByPipelined(Object key, List<Object> hashKeys) {
		try {
			return redisTemplate.executePipelined(new RedisCallback<String>() {
	            @Override
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
	            	for (int i = 0; i < hashKeys.size(); i++) {
	                    connection.hGet(key.toString().getBytes(), hashKeys.get(i).toString().getBytes());
	                }
	                return null;
	            }
	        });
			
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取原字符串 并 重新赋值
	 * @param key 键
	 * @param value 新值
	 * @return 
	 */
	public static Object getAndSet(Object key, Object value) {
		try {
			return redisTemplate.opsForValue().getAndSet(key, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 在原字符串末尾 追加字符串
	 * @param key 键
	 * @param value 值
	 */
	public static void append(Object key, String value) {
		try {
			redisTemplate.opsForValue().append(key, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入对象
	 * @param key 键
	 * @param entity 对象
	 */
	public static <T> void setEntity(Object key, T entity) {
		try {
			redisTemplate.opsForValue().set(key, JSONObject.toJSONString(entity));
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入对象  并 设置过期时间
	 * @param key 键
	 * @param entity 对象
	 * @param time 时间
	 * @param unit 时间粒度
	 */
	public static <T> void setEntityAndTimeout(Object key, T value, long time, TimeUnit unit) {
		try {
			redisTemplate.opsForValue().set(key, JSONObject.toJSONString(value), time, unit);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入对象集合
	 * @param key 键
	 * @param value 集合对象
	 */
	public static <T> void setEntityList(Object key, List<T> entityList) {
		try {
			redisTemplate.opsForValue().set(key, JSONObject.toJSONString(entityList));
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入对象集合 并 设置过期时间
	 * @param key 键
	 * @param entityList 对象集合
	 * @param time 时间
	 * @param unit 时间粒度
	 */
	public static <T> void setEntityListAndTimeout(Object key, List<T> entityList, long time, TimeUnit unit) {
		try {
			redisTemplate.opsForValue().set(key, JSONObject.toJSONString(entityList), time, unit);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取对象
	 * @param key 键
	 * @return 
	 */
	public static <T> T getEntity(Object key, Class<T> clz) {
		try {
			Object obj = redisTemplate.opsForValue().get(key);
			if (obj != null) {
				return jsonToEntity((String) obj, clz);
			}
			return null;
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取对象集合
	 * @param key 键
	 * @return 
	 */
	public static <T> List<T> getEntityList(Object key, Class<T> clz) {
		try {
			Object obj = redisTemplate.opsForValue().get(key);
			if (obj != null) {
				return jsonToList((String) obj, clz);
			}
			return Collections.emptyList();
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取对象 并 重新赋值
	 * @param key 键
	 * @param newEntity 新对象
	 * @return 
	 */
	public static <T> T getEntityAndSet(Object key, T newEntity, Class<T> clz) {
		try {
			Object obj = redisTemplate.opsForValue().getAndSet(key, JSONObject.toJSONString(newEntity));
			if (obj != null) {
				return jsonToEntity((String) obj, clz);
			}
			return null;
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取对象集合 并 重新赋值
	 * @param key 键
	 * @param newEntityList 新对象集合
	 * @return 
	 */
	public static <T> List<T> getEntityListAndSet(Object key, List<T> newEntityList, Class<T> clz) {
		try {
			Object obj = redisTemplate.opsForValue().getAndSet(key, JSONObject.toJSONString(newEntityList));
			if (obj != null) {
				return jsonToList((String) obj, clz);
			}
			return Collections.emptyList();
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 转换成 实体类对象
	 * @param text JSON字符串
	 * @param clz 对象的类型
	 * @return
	 */
	public static <T> T jsonToEntity(String text, Class<T> clz) {
		try {
			return JSONArray.parseObject(text, clz);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 转换成 实体类对象集合
	 * @param text JSON字符串
	 * @param clz 对象的类型
	 * @return
	 */
	public static <T> List<T> jsonToList(String text, Class<T> clz) {
		try {
			return JSONArray.parseArray(text, clz);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return
	 */
	public static Boolean exist(Object key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 移除缓存数据
	 * @param key 键
	 */
	public static void delete(Object key) {
		try {
			redisTemplate.delete(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量移除缓存数据
	 * @param keys 键
	 */
	public static void deleteAll(List<Object> keys) {
		try {
			redisTemplate.delete(keys);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入Map
	 * @param key 键
	 * @param hashKey Map的key
	 * @param value Map的value
	 */
	public static void put(Object key, Object hashKey, Object hashValue) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, hashValue);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 存入Map
	 * @param key 键
	 * @param map
	 */
	public static void putAll(Object key, Map<? extends Object, ? extends Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 批量存入  
	 * @param key
	 * @param map
	 * @return 
	 */
	public static void putByPipelined(String key, Map<String, String> map) {
		try {
			redisTemplate.executePipelined(new RedisCallback<Object>() {
	           
				@Override
	            public String doInRedis(RedisConnection connection) throws DataAccessException {
					Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
					for (Entry<String, String> entry : map.entrySet()) {
						byteMap.put(entry.getKey().getBytes(), entry.getValue().getBytes());
					}
					connection.hMSet(key.getBytes(), byteMap);
	                return null;
	            }
				
	        });
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取Map
	 * @param key 键
	 */
	public static Map<Object, Object> getMap(Object key) {
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取Map中指定的值
	 * @param key 键
	 * @param hashKey Map的key
	 * @return
	 */
	public static Object getMapValue(Object key, Object hashKey) {
		try {
			return redisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取Map中所有的key
	 * @param key 键
	 * @return
	 */
	public static Set<Object> getMapKeys(Object key) {
		try {
			return redisTemplate.opsForHash().keys(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取Map中所有的值
	 * @param key 键
	 * @return
	 */
	public static List<Object> getMapValues(Object key) {
		try {
			return redisTemplate.opsForHash().values(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取Map的长度
	 * @param key 键
	 * @return
	 */
	public static long getMapSize(Object key) {
		try {
			return redisTemplate.opsForHash().size(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 判断Map中key是否存在
	 * @param key 键
	 * @param hashKey Map的key
	 * @return
	 */
	public static Boolean existMapKey(Object key, Object hashKey) {
		try {
			return redisTemplate.opsForHash().hasKey(key, hashKey);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 移除Map中指定的key
	 * @param key 键
	 * @param hashKey Map的key
	 */
	public static void deleteMapKey(Object key, Object hashKey) {
		try {
			redisTemplate.opsForHash().delete(key, hashKey);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取Map中自增数字
	 * @param key 键
	 * @param hashKey Map的key
	 * @param delta 递增的基数
	 * @return
	 */
	public static long getAddNumberForMap(Object key, Object hashKey, long delta) {
		try {
			return redisTemplate.opsForHash().increment(key, hashKey, delta);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}

	/**
	 * 获取自增数字
	 * @param key 键
	 * @param delta 递增的基数
	 * @return
	 */
	public static long getAddNumber(String key, long delta) {
		try {
			RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
			return counter.addAndGet(delta);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取自增数字,可用于自增ID的生成
	 * @param key 可当做数据库 表 的分类标识
	 * @param delta 递增的基数
	 * @return
	 */
	public static long getOnlyNumberId(String key, long delta) {
		try {
			RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
			return counter.addAndGet(delta);
		} catch (Exception e) {
			return IdUtils.numberId();
		}
	}
	
	
	/**
	 * 向左添加元素
	 * @param key 键
	 * @param value 值
	 */
	public static void leftPush(Object key, Object value) {
		try {
			redisTemplate.opsForList().leftPush(key, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 向左添加元素-集合
	 * @param key 键
	 * @param values 值（集合）
	 */
	public static void leftPushAll(Object key, Collection<Object> values) {
		try {
			redisTemplate.opsForList().leftPushAll(key, values);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 向右添加元素
	 * @param key 键
	 * @param value 值
	 */
	public static void rightPush(Object key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 向右添加元素-集合
	 * @param key 键
	 * @param values 值（集合）
	 */
	public static void rightPushAll(Object key, Collection<Object> values) {
		try {
			redisTemplate.opsForList().rightPushAll(key, values);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}

	/**
	 * 向左弹出元素
	 * @param key 键
	 * @return
	 */
	public static Object leftPop(Object key) {
		try {
			return redisTemplate.opsForList().rightPop(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 向右弹出元素
	 * @param key 键
	 * @return
	 */
	public static Object rightPop(Object key) {
		try {
			return redisTemplate.opsForList().rightPop(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取集合长度
	 * @param key 键
	 * @return
	 */
	public static long size(Object key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 获取集合中指定下标的元素
	 * @param key 键
	 * @param index 下标
	 * @return
	 */
	public static Object index(Object key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 集合添加指定下表元素
	 * @param key
	 * @param index
	 * @param value
	 */
	public static void addToList(Object key, int index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 删除List中的元素
	 * @param key
	 * @param value
	 */
	public static void removeToList(Object key, Object value) {
		try {
			redisTemplate.opsForList().remove(key, 0, value);
		} catch (Exception e) {
			if (e instanceof RedisConnectionFailureException) {
				throw new CustomException(SystemErrorEnum.REDIS_NOT_CONNECTION);
			} else {
				e.printStackTrace();
				throw new CustomException(SystemErrorEnum.REDIS_CACHE_ERROR);
			}
		}
	}
	
	/**
	 * 锁
	 * @param key 
	 * @return
	 */
	public static RLock getLock(String key) {
		return redissonClient().getLock(key);
	}
	
}
