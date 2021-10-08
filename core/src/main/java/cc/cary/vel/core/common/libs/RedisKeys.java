package cc.cary.vel.core.common.libs;

/**
 * redis key
 *
 * @author Cary
 * @date 2021/05/22
 */
public class RedisKeys {
  public static final String USER_LOGIN = "USER:LOGIN";
  public static final String USER_ROLES = "USER:ROLES";
  public static final String USER_PERMISSIONS = "USER:PERMISSIONS";

  public static final String API_AUTHORIZED = "API:AUTHORIZED";

  public static final String MENU = "MENU";

  public static final String CAPTCHA_PREFIX = "CAPTCHA:";

  public static final String BATCH_EXPORT_PREFIX = "BATCH_EXPORT:";

  public static final String BATCH_EXPORT_CANCEL = "BATCH_EXPORT:CANCEL:";

  public static final String USER_LOCK_LIMIT = "USER:LOCK:LIMIT:";
}
