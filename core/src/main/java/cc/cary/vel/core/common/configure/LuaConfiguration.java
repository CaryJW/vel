package cc.cary.vel.core.common.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * lua 脚本配置
 *
 * @author Cary
 * @date 2021/05/22
 */
@Configuration
public class LuaConfiguration {
  @Bean
  public RedisScript<Long> limitScript() {
    return RedisScript.of(new ClassPathResource("script/limit.lua"), Long.class);
  }
}
