package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.sys.entities.Permission;
import cc.cary.vel.core.sys.vo.PermissionVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * IPermissionService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface IPermissionService extends IService<Permission> {
  /**
   * 根据用户ID查询权限
   *
   * @param userId
   * @return
   */
  List<Permission> findUserPermissionsByUserId(long userId);

  /**
   * 根据角色ID查询权限
   *
   * @param roleIds
   * @return
   */
  List<Permission> findUserPermissionsByRoleId(long roleIds);

  /**
   * 根据角色IDs查询权限IDs
   *
   * @param roleIds
   * @return
   */
  List<Long> findUserPermIdsByRoleId(long roleIds);

  /**
   * 权限tree
   *
   * @return
   */
  List<Permission> tree();

  /**
   * 查询用户权限tree
   *
   * @param userId
   * @return
   */
  List<Permission> tree(long userId);

  /**
   * 添加权限
   *
   * @param permissionVo
   * @return
   */
  Permission add(PermissionVo permissionVo);

  /**
   * 更新权限
   *
   * @param permissionVo
   * @return
   */
  Permission update(PermissionVo permissionVo);

  /**
   * 删除权限
   *
   * @param id
   */
  void delete(long id);
}
