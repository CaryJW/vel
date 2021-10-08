package cc.cary.vel.core.common.authentication;

import cc.cary.vel.core.common.libs.ResultCode;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.common.utils.RenderUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * JwtFilter
 * 执行顺序 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 *
 * @author Cary
 * @date 2021/05/22
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
//  /**
//   * 跨域支持
//   * 在shiro拦截时，若未登录等，将会自动重定向到登录或无权限，会出现跨域失效问题，前后端分离则不存在此种情况
//   */
//  @Override
//  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//    httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//    httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//      httpServletResponse.setStatus(HttpStatus.OK.value());
//      return false;
//    }
//    return super.preHandle(request, response);
//  }

  /**
   * 检查头部是否有token
   *
   * @param request
   * @param response
   * @return
   */
  @Override
  protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
    return StringUtils.isNoneBlank(getAuthzHeader(request));
  }

  @SneakyThrows
  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    RenderUtils.renderJson((HttpServletResponse) response, ResultData.fail(ResultCode.UNAUTHORIZED));
    return false;
  }

  /**
   * 执行登录
   *
   * @param request
   * @param response
   * @return
   */
  @SneakyThrows
  @Override
  protected boolean executeLogin(ServletRequest request, ServletResponse response) {
    try {
      JwtToken jwtToken = new JwtToken(getAuthzHeader(request));
      getSubject(request, response).login(jwtToken);
    } catch (IncorrectCredentialsException | ExpiredCredentialsException | UnknownAccountException e) {
      RenderUtils.renderJson((HttpServletResponse) response, ResultData.fail(ResultCode.AUTHORIZED_ERROR, e.getMessage()));
      return false;
    } catch (LockedAccountException e) {
      RenderUtils.renderJson((HttpServletResponse) response, ResultData.fail(ResultCode.ACCOUNT_LOCKED, e.getMessage()));
      return false;
    }
    return true;
  }
}
