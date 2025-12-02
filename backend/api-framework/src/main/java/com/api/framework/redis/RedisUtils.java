package com.api.framework.redis;

import com.api.common.utils.SpringUtils;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 * 
 * @author api
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class RedisUtils
{
    /**
     * Spring管理RedisTemplate
     */
    private static RedisTemplate redisTemplate;

    /**
     * 设置spring redis template
     * 
     * @param redisTemplate
     */
    public static void setRedisTemplate(RedisTemplate redisTemplate)
    {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * 获取缓存basic对象
     * 
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public static <T> T getCacheObject(String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存basic对象
     * 
     * @param key 缓存键值
     * @param value 缓存键值对应的数据
     */
    public static <T> void setCacheObject(String key, T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存basic对象并设置时间
     * 
     * @param key 缓存键值
     * @param value 缓存键值对应的数据
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public static <T> void setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     * 
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(String key, long timeout)
    {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取有效时间
     * 
     * @param key Redis键
     * @return 有效时间
     */
    public static long getExpire(String key)
    {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * 
     * @param key 键
     * @return true 存在 false不存在
     */
    public static Boolean hasKey(String key)
    {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除单个对象
     * 
     * @param key
     */
    public static boolean deleteObject(String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * 
     * @param collection 多个对象
     * @return
     */
    public static long deleteObject(Collection collection)
    {
        try
        {
            return redisTemplate.delete(collection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 缓存List数据
     * 
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public static <T> long setCacheList(String key, List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     * 
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public static <T> List<T> getCacheList(String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * 
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public static <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     * 
     * @param key
     * @return
     */
    public static <T> Set<T> getCacheSet(String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     * 
     * @param key
     * @param dataMap
     */
    public static <T> void setCacheMap(String key, Map<String, T> dataMap)
    {
        if (dataMap != null)
        {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     * 
     * @param key
     * @return
     */
    public static <T> Map<String, T> getCacheMap(String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     * 
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public static <T> void setCacheMapValue(String key, String hKey, T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     * 
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public static <T> T getCacheMapValue(String key, String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     * 
     * @param key
     * @param hkey
     */
    public static void delCacheMapValue(String key, String... hkey)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (hkey.length > 1)
        {
            hashOperations.delete(key, hkey);
        }
        else
        {
            hashOperations.delete(key, hkey[0]);
        }
    }

    /**
     * 获取多个Hash中的数据
     * 
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static <T> List<T> getMultiCacheMapValue(String key, Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     * 
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public static Collection<String> keys(String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 缓存ZSet
     * 
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public static <T> ZSetOperations<String, T> setCacheZSet(String key, Set<T> dataSet)
    {
        ZSetOperations<String, T> zSetOperations = redisTemplate.opsForZSet();
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            zSetOperations.add(key, it.next(), 0);
        }
        return zSetOperations;
    }

    /**
     * 获得缓存的zset
     * 
     * @param key
     * @return
     */
    public static <T> Set<T> getCacheZSet(String key)
    {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}