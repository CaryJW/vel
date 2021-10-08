package cc.cary.vel.adminapi.authentication;

import cc.cary.vel.core.common.authentication.JwtToken;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.JwtUtils;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.sys.entities.AdminUser;
import cc.cary.vel.core.sys.entities.Permission;
import cc.cary.vel.core.sys.entities.Role;
import cc.cary.vel.core.sys.services.IAdminUserService;
import cc.cary.vel.core.sys.services.IPermissionService;
import cc.cary.vel.core.sys.services.IRoleService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JwtShiroRealm
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
public class JwtShiroRealm extends AuthorizingRealm {
  @Autowired
  IAdminUserService adminUserService;
  @Autowired
  IRoleService roleService;
  @Autowired
  IPermissionService permissionService;
  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  RedisUtils redisUtils;

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
    AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
    String userIdStr = String.valueOf(user.getId());

    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    if (redisUtils.hmExists(RedisKeys.USER_ROLES, userIdStr)
        && redisUtils.hmExists(RedisKeys.USER_PERMISSIONS, userIdStr)) {
      // 缓存中获取角色和权限
      Set<String> roleSet = (Set<String>) redisUtils.hmGet(RedisKeys.USER_ROLES, userIdStr);
      Set<String> permissionSet = (Set<String>) redisUtils.hmGet(RedisKeys.USER_PERMISSIONS, userIdStr);

      simpleAuthorizationInfo.setRoles(roleSet);
      simpleAuthorizationInfo.setStringPermissions(permissionSet);
    } else {
      // 获取用户角色集
      List<Role> roleList = this.roleService.findUserRoleByUserId(user.getId());
      Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
      simpleAuthorizationInfo.setRoles(roleSet);

      // 获取用户权限集
      List<Permission> permissionList = this.permissionService.findUserPermissionsByUserId(user.getId());
      Set<String> permissionSet = permissionList.stream().distinct().map(Permission::getPerms).collect(Collectors.toSet());
      simpleAuthorizationInfo.setStringPermissions(permissionSet);

      // 存入redis
      redisUtils.hmSet(RedisKeys.USER_ROLES, userIdStr, roleSet);
      redisUtils.hmSet(RedisKeys.USER_PERMISSIONS, userIdStr, permissionSet);
    }
    return simpleAuthorizationInfo;
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
    Object tokenObj = redisUtils.hmGet(RedisKeys.USER_LOGIN, userId);
    if (tokenObj == null) {
      throw new ExpiredCredentialsException("登录过期，请重新登录！");
    }
    if (!StringUtils.equals(jwtToken, String.valueOf(tokenObj))) {
      throw new ExpiredCredentialsException("该账户已经在其他平台登录，请重新登录！");
    }

    AdminUser user = adminUserService.getById(userId);
    if (user == null) {
      throw new UnknownAccountException("非法账户");
    }
    if (Constants.ADMIN_USER_STATUS_LOCK == user.getStatus()) {
      throw new LockedAccountException("账号已被锁定,请联系管理员");
    }

    return new SimpleAuthenticationInfo(user, jwtToken, getName());
  }
}
