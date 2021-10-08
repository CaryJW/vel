package cc.cary.vel.serviceapi.authentication;

import cc.cary.vel.core.common.authentication.JwtFilter;
import cc.cary.vel.core.common.properties.ShiroProperties;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfig 配置类
 *
 * @author Cary
 * @date 2021/05/22
 */
@Configuration
public class ShiroConfig {
  @Autowired
  ShiroProperties shiroProperties;

  /**
   * JWT过滤器名称
   */
  private static final String JWT_FILTER_NAME = "JwtFilter";

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

    // 设置 securityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    // 添加自己的过滤器
    Map<String, Filter> filterMap = new LinkedHashMap<>();
    // 配置过滤器 对 『anon』不进行拦截 AnonymousFilter 放行请求 ，设置我们自定义的JWT过滤器
    filterMap.put("anon", new AnonymousFilter());
    filterMap.put(JWT_FILTER_NAME, new JwtFilter());
    shiroFilterFactoryBean.setFilters(filterMap);

    //指定路径和过滤器的对应关系
    LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    // 设置免认证 url
    for (String url : shiroProperties.getAnonUrl()) {
      filterChainDefinitionMap.put(url, "anon");
    }
    // 除上以外所有 url都必须自定义过滤器
    filterChainDefinitionMap.put("/**", JWT_FILTER_NAME);

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }


  @Bean
  public SecurityManager securityManager(JwtShiroRealm jwtShiroRealm) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 配置 SecurityManager，并注入shiroRealm
    securityManager.setRealm(jwtShiroRealm);
    // 禁用session
    securityManager.setSubjectDAO(subjectDAO());
    return securityManager;
  }

  @Bean
  public DefaultSubjectDAO subjectDAO() {
    DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
    defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
    return defaultSubjectDAO;
  }

  @Bean
  public DefaultSessionStorageEvaluator sessionStorageEvaluator() {
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    return defaultSessionStorageEvaluator;
  }
}
