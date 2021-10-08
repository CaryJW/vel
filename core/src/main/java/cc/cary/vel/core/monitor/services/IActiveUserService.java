package cc.cary.vel.core.monitor.services;

import cc.cary.vel.core.monitor.entities.ActiveUser;

import java.util.List;

/**
 * IActiveUserService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface IActiveUserService {
  /**
   * 根据用户名和当前用户ID查询在线用户
   *
   * @param username
   * @param currentUserId
   * @return
   */
  List<ActiveUser> list(String username, long currentUserId);

  /**
   * 踢出用户
   *
   * @param userId
   */
  void kickout(long userId);
}
