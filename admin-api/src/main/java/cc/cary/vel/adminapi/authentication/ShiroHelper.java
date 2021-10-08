package cc.cary.vel.adminapi.authentication;

import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.sys.entities.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ShiroHelper
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
public class ShiroHelper extends JwtShiroRealm {
  @Autowired
  RedisUtils redisUtils;

  /**
   * 获取当前用户的角色和权限集合
   *
   * @return AuthorizationInfo
   */
  public AuthorizationInfo getCurrentUserAuthorizationInfo() {
    return super.doGetAuthorizationInfo(null);
  }

  /**
   * 获取当前登录的用户
   *
   * @return
   */
  public AdminUser getCurrentUser() {
    return (AdminUser) SecurityUtils.getSubject().getPrincipal();
  }


  /**
   * 删除用户权限缓存
   */
  public void clearCache() {
    redisUtils.hmRemove(RedisKeys.USER_ROLES, String.valueOf(getCurrentUser().getId()));
    redisUtils.hmRemove(RedisKeys.USER_PERMISSIONS, String.valueOf(getCurrentUser().getId()));
  }

  /**
   * 退出登录
   */
  public void logout() {
    redisUtils.hmRemove(RedisKeys.USER_LOGIN, String.valueOf(getCurrentUser().getId()));
  }
}
