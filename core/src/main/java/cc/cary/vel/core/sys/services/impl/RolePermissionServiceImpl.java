package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.sys.entities.RolePermission;
import cc.cary.vel.core.sys.mapper.RolePermissionMapper;
import cc.cary.vel.core.sys.services.IRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * RolePermissionServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
  @Override
  public void deleteByRoleId(long roleId) {
    remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
  }

  @Override
  public void deleteByPermissionId(long permId) {
    remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, permId));
  }
}
