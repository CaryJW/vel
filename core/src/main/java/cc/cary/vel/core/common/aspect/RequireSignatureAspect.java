package cc.cary.vel.core.common.aspect;

import cc.cary.vel.core.common.annotation.RequireSignature;
import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.signature.ISignatureService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求签名验证
 *
 * @author Cary
 * @date 2021/8/20
 */
public class RequireSignatureAspect {
  @Autowired
  private ISignatureService signatureService;

  @Pointcut("@annotation(cc.cary.vel.core.common.annotation.RequireSignature)")
  public void pointcut() {
    // do nothing
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    RequireSignature signatureAnnotation = method.getAnnotation(RequireSignature.class);
    // secret
    String secret = "";
    // 签名验证
    if (signatureAnnotation.open() && !signatureService.verify(request, secret)) {
      throw new AppException("签名错误");
    }
    // 执行方法
    return point.proceed();
  }
}
