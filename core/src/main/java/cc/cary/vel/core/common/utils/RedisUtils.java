package cc.cary.vel.core.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
@Slf4j
public class RedisUtils {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  /**
   * 写入缓存
   *
   * @param key
   * @param value
   */
  public void set(final String key, Object value) {
    ValueOperations<String, Object> operations = redisTemplate.opsForValue();
    operations.set(key, value);
  }

  /**
   * 写入缓存设置时效时间
   *
   * @param key
   * @param value
   * @param expireTime
   * @param timeUnit
   */
  public void set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
    ValueOperations<String, Object> operations = redisTemplate.opsForValue();
    operations.set(key, value);
    redisTemplate.expire(key, expireTime, timeUnit);
  }

  /**
   * 设置时效时间
   *
   * @param key
   * @param expireTime
   * @param timeUnit
   */
  public void expire(final String key, Long expireTime, TimeUnit timeUnit) {
    redisTemplate.expire(key, expireTime, timeUnit);
  }

  /**
   * 批量删除对应的value
   *
   * @param keys
   */
  public void remove(final String... keys) {
    for (String key : keys) {
      remove(key);
    }
  }

  /**
   * 批量删除key
   *
   * @param pattern
   */
  public void removePattern(final String pattern) {
    Set<String> keys = redisTemplate.keys(pattern);
    if (keys.size() > 0) {
      redisTemplate.delete(keys);
    }
  }

  /**
   * 删除对应的value
   *
   * @param key
   */
  public void remove(final String key) {
    if (exists(key)) {
      redisTemplate.delete(key);
    }
  }

  /**
   * 判断缓存中是否有对应的value
   *
   * @param key
   * @return
   */
  public boolean exists(final String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 读取缓存
   *
   * @param key
   * @return
   */
  public Object get(final String key) {
    ValueOperations<String, Object> operations = redisTemplate.opsForValue();
    return operations.get(key);
  }

  /**
   * 判断哈希字段是否存在
   *
   * @param key
   * @param hashKey
   * @return
   */
  public boolean hmExists(String key, Object hashKey) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    return hash.hasKey(key, hashKey);
  }

  /**
   * 哈希 添加
   *
   * @param key
   * @param hashKey
   * @param value
   */
  public void hmSet(String key, Object hashKey, Object value) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    hash.put(key, hashKey, value);
  }

  /**
   * 哈希获取数据
   *
   * @param key
   * @param hashKey
   * @return
   */
  public Object hmGet(String key, Object hashKey) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    return hash.get(key, hashKey);
  }

  /**
   * 哈希获取所有数据
   *
   * @param key
   * @return
   */
  public Map<Object, Object> hmGetAll(String key) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    return hash.entries(key);
  }

  /**
   * 删除哈希数据
   *
   * @param key
   * @param hashKey
   * @return
   */
  public Object hmRemove(String key, Object hashKey) {
    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
    return hash.delete(key, hashKey);
  }

  public List<Object> hmRemove(String key, Object... hashKey) {
    List<Object> list = new ArrayList<>();
    for (Object k : hashKey) {
      HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
      list.add(hash.delete(key, hashKey));
    }
    return list;
  }

  /**
   * 列表添加
   *
   * @param k
   * @param v
   */
  public void lPush(String k, Object v) {
    ListOperations<String, Object> list = redisTemplate.opsForList();
    list.rightPush(k, v);
  }

  /**
   * 列表获取
   *
   * @param k
   * @param l
   * @param l1
   * @return
   */
  public List<Object> lRange(String k, long l, long l1) {
    ListOperations<String, Object> list = redisTemplate.opsForList();
    return list.range(k, l, l1);
  }

  /**
   * 集合添加
   *
   * @param key
   * @param value
   */
  public void add(String key, Object value) {
    SetOperations<String, Object> set = redisTemplate.opsForSet();
    set.add(key, value);
  }

  /**
   * 集合获取
   *
   * @param key
   * @return
   */
  public Set<Object> setMembers(String key) {
    SetOperations<String, Object> set = redisTemplate.opsForSet();
    return set.members(key);
  }

  /**
   * 有序集合添加
   *
   * @param key
   * @param value
   * @param scoure
   */
  public void zAdd(String key, Object value, double scoure) {
    ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
    zset.add(key, value, scoure);
  }

  /**
   * 自增
   *
   * @param key
   * @return
   */
  public Long increment(String key) {
    return redisTemplate.opsForValue().increment(key);
  }

  /**
   * 自增
   *
   * @param key
   * @param delta 增大数值
   * @return
   */
  public Long increment(String key, long delta) {
    return redisTemplate.opsForValue().increment(key, delta);
  }

  /**
   * 自减
   *
   * @param key
   * @return
   */
  public Long decrement(String key) {
    return redisTemplate.opsForValue().decrement(key);
  }

  /**
   * 自减
   *
   * @param key
   * @param delta 减少数值
   * @return
   */
  public Long decrement(String key, long delta) {
    return redisTemplate.opsForValue().decrement(key, delta);
  }

  /**
   * 有序集合获取
   *
   * @param key
   * @param scoure
   * @param scoure1
   * @return
   */
  public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
    ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
    return zset.rangeByScore(key, scoure, scoure1);
  }

  /**
   * 锁账户
   *
   * @param id
   * @return
   */
  public boolean lockAccount(String id) {
    ValueOperations<String, Object> operations = redisTemplate.opsForValue();
    try {
      while (true) {
        boolean flag = operations.setIfAbsent("LK" + id, id);
        if (flag) {
          log.info("锁定账户成功！mid=" + id);
          return true;
        }
        Thread.sleep(500);
      }
    } catch (Exception e) {
      log.error("锁账户异常:", e);
    }
    return false;
  }

  /**
   * 解锁账户
   *
   * @param id
   * @return
   */
  public void unLockAccount(String id) {
    remove("LK" + id);
    log.info("解锁账户成功！mid=" + id);
  }

  /**
   * 设置bitmap
   *
   * @param key
   * @param offset 偏移量
   * @param value  值
   * @return
   */
  public boolean setBit(String key, int offset, boolean value) {
    return redisTemplate.opsForValue().setBit(key, offset, value);
  }

  /**
   * 获取bitmap
   *
   * @param key
   * @param offset
   * @return
   */
  public Boolean getBit(String key, int offset) {
    return redisTemplate.opsForValue().getBit(key, offset);
  }

  /**
   * 获取bitmap 1的数量
   *
   * @param key
   * @return
   */
  public long bitCount(String key) {
    // JSON.toJSONBytes(key) 需要手动序列化key
    return redisTemplate.execute((RedisCallback<Long>) con -> {
      return con.bitCount(JSON.toJSONBytes(key));
    });
  }

  /**
   * 执行 无符号u 类型的 bitfield get
   *
   * @param key
   * @param limit
   * @param offset
   * @return
   */
  public List<Long> bitfieldGet(String key, int limit, int offset) {
    return redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset));
  }
}

