package cc.cary.vel.core.common.aspect;

import cc.cary.vel.core.common.annotation.Limit;
import cc.cary.vel.core.common.entities.LimitType;
import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 接口限流
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {
  @Autowired
  @Qualifier("limitScript")
  private RedisScript<Long> limitScript;
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Pointcut("@annotation(cc.cary.vel.core.common.annotation.Limit)")
  public void pointcut() {
    // do nothing
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

    Object object = point.getTarget();
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    Limit limitAnnotation = method.getAnnotation(Limit.class);
    LimitType limitType = limitAnnotation.limitType();
    String name = limitAnnotation.name();
    String key;
    String ip = IpUtils.getIpAddr(request);
    int limitPeriod = limitAnnotation.period();
    int limitCount = limitAnnotation.count();
    switch (limitType) {
      case CUSTOMER:
        key = limitAnnotation.key();
        break;
      case IP:
        key = String.join(limitAnnotation.key(), ip);
        break;
      case DEFAULT:
        key = String.join(".", limitAnnotation.key(), object.getClass().getCanonicalName(), method.getName() + "()");
        break;
      default:
        key = StringUtils.upperCase(method.getName());
    }

    List<String> keys = Collections.singletonList(String.join("", limitAnnotation.prefix(), key));

    Long count = redisTemplate.execute(limitScript, keys, limitCount, limitPeriod);
    log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);

    if (count != null && count.intValue() <= limitCount) {
      return point.proceed();
    } else {
      throw new AppException("接口访问超出频率限制");
    }
  }
}
