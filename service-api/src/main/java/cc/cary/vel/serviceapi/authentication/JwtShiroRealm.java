package cc.cary.vel.serviceapi.authentication;

import cc.cary.vel.core.business.entities.User;
import cc.cary.vel.core.business.services.IUserService;
import cc.cary.vel.core.common.authentication.JwtToken;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.JwtUtils;
import cc.cary.vel.core.common.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JwtShiroRealm
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
public class JwtShiroRealm extends AuthorizingRealm {
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private RedisUtils redisUtils;
  @Autowired
  private IUserService userService;

  /**
   * 一定要重写support()方法，在后面的身份验证器中会用到
   *
   * @param token
   * @return
   */
  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
  }

  /**
   * 授权模块，获取用户角色和权限
   *
   * @param principal
   * @return
   */
  @Override
  @SuppressWarnings("unchecked")
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
    return new SimpleAuthorizationInfo();
  }

  /**
   * 用户认证
   *
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // 获取token
    String jwtToken = (String) token.getPrincipal();
    //解密
    Claims claims = jwtUtils.getClaimByToken(jwtToken);

    if (claims == null) {
      throw new IncorrectCredentialsException("登录过期，请重新登录！");
    }
    // 获取用户id
    String userId = String.valueOf(claims.getSubject());

    //redis防止重复登录
    Object tokenObj = redisUtils.hmGet(RedisKeys.API_AUTHORIZED, userId);
    if (tokenObj == null || !StringUtils.equals(jwtToken, String.valueOf(tokenObj))) {
      throw new ExpiredCredentialsException("登录过期，请重新登录！");
    }

    User user = userService.getById(userId);
    if (user == null) {
      throw new UnknownAccountException("非法账户");
    }
    if (Constants.USER_STATUS_LOCK == user.getStatus()) {
      throw new LockedAccountException("账号已被锁定,请联系管理员");
    }

    return new SimpleAuthenticationInfo(user, jwtToken, getName());
  }
}
