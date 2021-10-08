package cc.cary.vel.core.common.aspect;

import cc.cary.vel.core.business.entities.User;
import cc.cary.vel.core.common.properties.ShiroProperties;
import cc.cary.vel.core.common.utils.HttpContextUtils;
import cc.cary.vel.core.common.utils.IpUtils;
import cc.cary.vel.core.monitor.entities.Log;
import cc.cary.vel.core.monitor.services.ILogService;
import cc.cary.vel.core.sys.entities.AdminUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
  @Autowired
  private ILogService logService;
  @Autowired
  private ShiroProperties shiroProperties;

  @Pointcut("@annotation(cc.cary.vel.core.common.annotation.Log)")
  public void pointcut() {
    // do nothing
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    long beginTime = System.currentTimeMillis();

    // 执行方法
    Object result = point.proceed();

    HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
    // 设置 IP地址
    String ip = IpUtils.getIpAddr(request);
    // 执行时长(毫秒)
    long time = System.currentTimeMillis() - beginTime;
    if (shiroProperties.isAopLog()) {
      // 保存日志
      Object user = SecurityUtils.getSubject().getPrincipal();
      Log log = new Log();
      if (user instanceof AdminUser) {
        log.setUsername(((AdminUser) user).getUsername());
      } else if (user instanceof User) {
        log.setUsername(((User) user).getUsername());
      } else {
        log.setUsername("系统");
      }
      log.setIp(ip);
      log.setTime(time);
      logService.saveLog(point, log);
    }
    return result;
  }
}
