package cc.cary.vel.core.common.configure;

import cc.cary.vel.core.common.properties.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * KeyStringRedisSerializer
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
public class KeyStringRedisSerializer implements RedisSerializer<String> {
  @Autowired
  private SysProperties sysProperties;

  @Override
  public byte[] serialize(String s) throws SerializationException {
    String keyPrefix = sysProperties.getCachePrefix();
    String key = keyPrefix + s;
    return key.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String deserialize(byte[] bytes) throws SerializationException {
    String keyPrefix = sysProperties.getCachePrefix();
    return new String(bytes, StandardCharsets.UTF_8).replaceFirst(keyPrefix, "");
  }
}
