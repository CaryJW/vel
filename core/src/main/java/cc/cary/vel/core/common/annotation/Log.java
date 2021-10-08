package cc.cary.vel.core.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author Cary
 * @date 2021/05/22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
  // 操作内容
  String value() default "";

  // 日志类型：0 后台，1 api
  int type() default 0;
}
