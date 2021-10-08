package cc.cary.vel.core.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * HttpContextUtils
 *
 * @author Cary
 * @date 2021/05/22
 */
public class HttpContextUtils {
  private HttpContextUtils() {

  }

  public static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
  }
}
