package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.sys.entities.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * IRolePermissionService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface IRolePermissionService extends IService<RolePermission> {
  /**
   * 根据角色ID删除权限
   *
   * @param roleId
   */
  void deleteByRoleId(long roleId);

  /**
   * 根据权限ID删除权限
   *
   * @param permId
   */
  void deleteByPermissionId(long permId);
}
