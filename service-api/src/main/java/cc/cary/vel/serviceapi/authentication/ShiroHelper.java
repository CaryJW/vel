package cc.cary.vel.serviceapi.authentication;

import cc.cary.vel.core.business.entities.User;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.RedisUtils;
import org.apache.shiro.SecurityUtils;
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
   * 获取当前登录的用户
   *
   * @return
   */
  public User getCurrentUser() {
    return (User) SecurityUtils.getSubject().getPrincipal();
  }

  /**
   * 退出登录
   */
  public void logout() {
    redisUtils.hmRemove(RedisKeys.API_AUTHORIZED, String.valueOf(getCurrentUser().getId()));
  }
}
