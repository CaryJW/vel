package cc.cary.vel.core.common.configure;

import cc.cary.vel.core.common.properties.SysProperties;
import cc.cary.vel.core.common.upload.UploadUtil;
import cc.cary.vel.core.common.upload.impl.AliOssUtil;
import cc.cary.vel.core.common.upload.impl.LocalUploadUtil;
import cc.cary.vel.core.common.xss.XssFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * vel配置
 *
 * @author Cary
 * @date 2021/05/22
 */
@Configuration
public class VelConfigure {
  @Autowired
  private SysProperties sysProperties;

  /**
   * 配置fastjson
   *
   * @return
   */
  @Bean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    //1.需要定义一个convert转换消息的对象;
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    //2:添加fastJson的配置信息;
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
    //3处理中文乱码问题
    List<MediaType> fastMediaTypes = new ArrayList<>();
    fastMediaTypes.add(MediaType.APPLICATION_JSON);
    //4.在convert中添加配置信息.
    fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
    return new HttpMessageConverters(converter);
  }

  /**
   * 配置异步线程池
   *
   * @return
   */
  @Bean("velAsyncThreadPool")
  public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(20);
    executor.setQueueCapacity(200);
    executor.setKeepAliveSeconds(30);
    executor.setThreadNamePrefix("User-Async-Thread");
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(60);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }

  /**
   * 注册 XssFilter 过滤器
   */
  @Bean
  @SuppressWarnings("all")
  public FilterRegistrationBean xssFilterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new XssFilter());
    filterRegistrationBean.setOrder(1);
    filterRegistrationBean.setEnabled(true);
    filterRegistrationBean.addUrlPatterns("/*");
    Map<String, String> initParameters = new HashMap<>();
    initParameters.put("excludes", StringUtils.join(sysProperties.getXss().getExcludes(), ","));
    initParameters.put("isIncludeRichText", sysProperties.getXss().getIsIncludeRichText().toString());
    filterRegistrationBean.setInitParameters(initParameters);
    return filterRegistrationBean;
  }

  /**
   * 配置上传工具
   *
   * @return
   */
  @Bean
  public UploadUtil uploadUtil() {
    UploadUtil uploadUtil;
    switch (sysProperties.getUploadType()) {
      case ALI:
        uploadUtil = new AliOssUtil();
        break;
      default:
        uploadUtil = new LocalUploadUtil();
        break;
    }
    return uploadUtil;
  }
}
