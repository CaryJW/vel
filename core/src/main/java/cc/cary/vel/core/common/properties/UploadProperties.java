package cc.cary.vel.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * UploadProperties
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

  @Data
  public static class LocalConfig {
    private String localhost;
    private String filePath;
  }

  @Data
  public static class AliConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String publicBucketName;
    private String filePath;
  }

  private AliConfig ali = new AliConfig();

  private LocalConfig local = new LocalConfig();
}
