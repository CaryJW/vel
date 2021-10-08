package cc.cary.vel.core.monitor.services.impl;

import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.JwtUtils;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.monitor.entities.ActiveUser;
import cc.cary.vel.core.monitor.services.IActiveUserService;
import cc.cary.vel.core.sys.entities.AdminUser;
import cc.cary.vel.core.sys.services.IAdminUserService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ActiveUserServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class ActiveUserServiceImpl implements IActiveUserService {
  @Autowired
  private RedisUtils redisUtils;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private IAdminUserService adminUserService;

  @Override
  public List<ActiveUser> list(String username, long currentUserId) {
    Map<Object, Object> login = redisUtils.hmGetAll(RedisKeys.USER_LOGIN);
    Set<Long> userIds = login.keySet().stream().map(String::valueOf).map(Long::parseLong).collect(Collectors.toSet());
    List<AdminUser> users = adminUserService.listByIds(userIds);

    List<ActiveUser> activeUsers = new ArrayList<>();
    for (AdminUser u : users) {
      if (StringUtils.isNoneEmpty(username) && !u.getUsername().equals(username)) {
        continue;
      }
      ActiveUser au = new ActiveUser();
      au.setUserId(u.getId());
      au.setUsername(u.getUsername());
      au.setStatus(getStatus((String) login.get(u.getId() + "")));
      au.setCurrent(false);
      au.setLoginTime(u.getLoginTime());
      if (u.getId() == currentUserId) {
        au.setCurrent(true);
      }
      activeUsers.add(au);
    }
    return activeUsers;
  }

  @Override
  public void kickout(long userId) {
    redisUtils.hmRemove(RedisKeys.USER_LOGIN, String.valueOf(userId));
  }

  private int getStatus(String token) {
    Claims claims = jwtUtils.getClaimByToken(token);
    if (claims == null) {
      return Constants.ACTIVE_USER_STATUS_OFFLINE;
    }
    return Constants.ACTIVE_USER_STATUS_ONLINE;
  }
}
