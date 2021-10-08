package cc.cary.vel.core.common.entities;

/**
 * 限流类型
 *
 * @author Cary
 * @date 2021/05/22
 */
public enum LimitType {
  /**
   * 传统类型，根据key限制
   */
  CUSTOMER,
  /**
   * 根据 IP地址限制
   */
  IP,
  /**
   * 根据请求类+方法限制
   */
  DEFAULT
}
