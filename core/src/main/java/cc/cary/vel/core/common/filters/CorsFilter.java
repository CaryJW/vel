package cc.cary.vel.core.common.filters;

import cc.cary.vel.core.common.properties.SysProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域处理
 *
 * @author Cary
 * @date 2021/8/16
 */
@Component
@Slf4j
public class CorsFilter implements Filter {
  @Autowired
  private SysProperties sysProperties;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    httpServletResponse.setHeader("Access-control-Allow-Origin", sysProperties.getCors().getOrigin());
    httpServletResponse.setHeader("Access-Control-Allow-Methods", sysProperties.getCors().getMethods());
    httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (Exception e) {
      log.error("CORS过滤器放行异常:", e);
    }
  }
}
