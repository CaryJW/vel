package cc.cary.vel.core.common.configure;

import cc.cary.vel.core.common.entities.UploadType;
import cc.cary.vel.core.common.properties.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 *
 * @author Cary
 * @date 2021/05/22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private SysProperties sysProperties;
  @Value("${upload.local.file-path}")
  private String filePath;
  @Value("${upload.local.path-pattens}")
  private String pathPattens;

  /**
   * 配置资源访问
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (sysProperties.getUploadType() == UploadType.LOCAL) {
      // 静态资源---图片url地址
      registry.addResourceHandler("/" + pathPattens + "/**").addResourceLocations("file:" + filePath);
    }
  }

}
