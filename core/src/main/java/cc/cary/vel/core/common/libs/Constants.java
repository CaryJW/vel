package cc.cary.vel.core.common.libs;

/**
 * 常量信息
 *
 * @author Cary
 */
public class Constants {
  /**
   * 菜单类型
   */
  public static final int MENU_NAVBAR = 0;
  public static final int MENU_BUTTON = 1;
  public static final int MENU_LABEL = 2;

  /**
   * 管理员用户状态
   * ADMIN_USER_STATUS_NORMAL 正常
   * ADMIN_USER_STATUS_LOCK 锁定
   */
  public static final int ADMIN_USER_STATUS_NORMAL = 0;
  public static final int ADMIN_USER_STATUS_LOCK = 1;

  /**
   * 在线用户状态
   * ACTIVE_USER_STATUS_OFFLINE 离线
   * ACTIVE_USER_STATUS_ONLINE 在线
   */
  public static final int ACTIVE_USER_STATUS_OFFLINE = 0;
  public static final int ACTIVE_USER_STATUS_ONLINE = 1;

  /**
   * 用户状态
   * USER_STATUS_NORMAL 正常
   * USER_STATUS_LOCK 锁定
   */
  public static final int USER_STATUS_NORMAL = 0;
  public static final int USER_STATUS_LOCK = 1;

  /**
   * 日志类型
   * ADMIN 后台
   * API api
   */
  public static final int ADMIN = 0;
  public static final int API = 1;

  /**
   * 允许下载的文件类型，根据需求自己添加（小写）
   */
  public static final String[] VALID_FILE_TYPE = {"xlsx", "zip"};

  /**
   * 文件导出状态
   * FILE_EXPORT_STATUS_NORMAL 进行中
   * FILE_EXPORT_STATUS_COMPLETE 完成
   * FILE_EXPORT_STATUS_FAIL 失败
   * FILE_EXPORT_STATUS_DELETED 已删除
   * FILE_EXPORT_STATUS_CANCEL 已取消
   */
  public static final int FILE_EXPORT_STATUS_NORMAL = 0;
  public static final int FILE_EXPORT_STATUS_COMPLETE = 1;
  public static final int FILE_EXPORT_STATUS_FAIL = 2;
  public static final int FILE_EXPORT_STATUS_DELETED = 3;
  public static final int FILE_EXPORT_STATUS_CANCEL = 4;
}
