package cc.cary.vel.core.common.libs;

/**
 * api相应状态码
 *
 * @author Cary
 */
public class ResultCode {
  /**
   * 成功状态码
   */
  public static Integer SUCCESS = 20000;
  /**
   * 失败状态码
   */
  public static Integer ERROR = 20001;
  /**
   * 授权错误
   */
  public static Integer AUTHORIZED_ERROR = 40000;
  /**
   * 未授权
   */
  public static Integer UNAUTHORIZED = 40001;
  /**
   * 账户被锁定
   */
  public static Integer ACCOUNT_LOCKED = 40002;
  /**
   * 系统内部异常
   */
  public static Integer SERVER_ERROR = 50000;
}
