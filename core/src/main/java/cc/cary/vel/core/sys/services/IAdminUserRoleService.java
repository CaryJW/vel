package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.sys.entities.AdminUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * IAdminUserRoleService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface IAdminUserRoleService extends IService<AdminUserRole> {
  /**
   * 根据用户ID删除角色
   *
   * @param userId
   */
  void deleteRoleByAdminUserId(long userId);

  /**
   * 用户角色ID查询用户
   *
   * @param roleId
   * @return List<Long>
   */
  List<Long> getAdminUserIdsByRoleId(long roleId);
}
