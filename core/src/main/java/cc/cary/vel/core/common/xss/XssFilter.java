package cc.cary.vel.core.common.xss;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Xss攻击拦截器
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
public class XssFilter implements Filter {

  /**
   * 是否过滤富文本内容
   */
  private boolean flag = false;

  private List<String> excludes = new ArrayList<>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
    if (StringUtils.isNotBlank(isIncludeRichText)) {
      flag = BooleanUtils.toBoolean(isIncludeRichText);
    }
    String temp = filterConfig.getInitParameter("excludes");
    if (temp != null) {
      String[] url = temp.split(",");
      excludes.addAll(Arrays.asList(url));
    }
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    if (handleExcludeUrl(req)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    log.info("------------ xss filter start ------------");
    XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest,
        flag);
    filterChain.doFilter(xssRequest, servletResponse);
  }

  private boolean handleExcludeUrl(HttpServletRequest request) {
    if (excludes == null || excludes.isEmpty()) {
      return false;
    }
    String url = request.getServletPath();
    return excludes.stream().map(pattern -> Pattern.compile("^" + pattern)).map(p -> p.matcher(url)).anyMatch(Matcher::find);
  }
}
