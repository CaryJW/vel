package cc.cary.vel.core.common.handler;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.ResultCode;
import cc.cary.vel.core.common.libs.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常绑定
 * <p>
 * Ordered.HIGHEST_PRECEDENCE) 提高优先级
 *
 * @author Cary
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(value = AppException.class)
  public ResultData handleAppException(AppException appException) {
    return ResultData.fail(appException.getCode(), appException.getMessage());
  }

  @ExceptionHandler(value = MissingServletRequestParameterException.class)
  public ResultData handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    return ResultData.fail(String.format("参数缺失：%s.", ex.getParameterName()));
  }

  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  public ResultData handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    return ResultData.fail(ex.getMessage());
  }

  @ExceptionHandler(value = BindException.class)
  protected ResultData handleBindException(BindException ex) {
    return ResultData.fail().put(ex.getBindingResult());
  }

  @ExceptionHandler(value = UnauthorizedException.class)
  protected ResultData handleUnauthorizedException(UnauthorizedException ex) {
    return ResultData.fail(ResultCode.UNAUTHORIZED, "你没有权限进行此操作");
  }

  @ExceptionHandler(value = Exception.class)
  public ResultData handleException(Exception e) {
    log.error("系统内部异常，异常信息", e);
    return ResultData.fail(ResultCode.SERVER_ERROR, "系统内部异常");
  }
}
