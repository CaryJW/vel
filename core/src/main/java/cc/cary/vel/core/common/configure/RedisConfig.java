package cc.cary.vel.core.common.configure;


import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;

/**
 * redis 配置
 *
 * @author Cary
 * @date 2021/05/22
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
  @Autowired
  private KeyStringRedisSerializer keyStringRedisSerializer;

  @Bean
  @Override
  public KeyGenerator keyGenerator() {// 可以支持使用@Cacheable不指定Key
    return (target, method, params) -> {
      StringBuilder sb = new StringBuilder();
      StringBuilder append = sb.append(target.getClass().getName());
      sb.append(method.getName());
      Arrays.stream(params).map(Object::toString).forEach(sb::append);
      return sb.toString();
    };
  }

  /**
   * 缓存管理器
   *
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
        .RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory);
    return builder.build();
  }

  /**
   * redis序列化方式选择：
   * 1.默认的JdkSerializationRedisSerializer序列化方式，其编码是ISO-8859-1,会出现乱码问题
   * 2.StringRedisSerializer序列化方式，其编码是UTF-8,可以解决乱码问题；序列化字符串无双引号
   * 3.Jackson2JsonRedisSerializer序列化方式，其编码是UTF-8,可以解决乱码问题，但是字符串会有一个双引号
   *
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(keyStringRedisSerializer);

    // 使用FastJson替换Jackon为默认Json序列化方式
    GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
    // 设置默认的Serialize，包含 keySerializer & valueSerializer
    redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);

    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setKeySerializer(keyStringRedisSerializer);
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
