package cc.cary.vel.core.common.properties;

import cc.cary.vel.core.common.entities.UploadType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SysProperties
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@Component
@ConfigurationProperties(prefix = "sys")
public class SysProperties {
  private String cachePrefix;
  private UploadType uploadType;
  private CorsConfig cors;
  private XssConfig xss = new XssConfig();

  @Data
  public static class XssConfig {
    private Boolean isIncludeRichText;

    private String[] excludes;
  }

  @Data
  public static class CorsConfig {
    private String origin;

    private String methods;
  }
}
