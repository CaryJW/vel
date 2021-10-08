package cc.cary.vel.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JwtProperties
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
  private String secret;

  private long expireTime;
}
