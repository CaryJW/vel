package cc.cary.vel.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ShiroProperties
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
  private boolean aopLog = true;

  private String[] anonUrl;
}
