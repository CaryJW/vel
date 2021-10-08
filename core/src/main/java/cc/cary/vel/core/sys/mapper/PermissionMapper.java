package cc.cary.vel.core.sys.mapper;

import cc.cary.vel.core.sys.entities.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * PermissionMapper
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface PermissionMapper extends BaseMapper<Permission> {
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
   * @param userId
   * @return
   */
  List<Permission> findUserPermissionsByRoleId(long userId);
}
