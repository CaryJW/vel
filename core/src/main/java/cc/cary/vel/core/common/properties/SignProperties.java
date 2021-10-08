package cc.cary.vel.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 签名属性
 *
 * @author Cary
 * @date 2021/8/20
 */
@Data
@Component
@ConfigurationProperties(prefix = "sys.sign")
public class SignProperties {
  private FieldName fieldName = new FieldName();

  @Data
  public static class FieldName {
    private String sign;
    private String key;
    private String time;
    private String secret;
  }
}
