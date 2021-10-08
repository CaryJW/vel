package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.sys.entities.AdminUserRole;
import cc.cary.vel.core.sys.mapper.AdminUserRoleMapper;
import cc.cary.vel.core.sys.services.IAdminUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AdminUserRoleServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper, AdminUserRole> implements IAdminUserRoleService {
  @Override
  public void deleteRoleByAdminUserId(long userId) {
    this.remove(new LambdaQueryWrapper<AdminUserRole>().eq(AdminUserRole::getAdminUserId, userId));
  }

  @Override
  public List<Long> getAdminUserIdsByRoleId(long roleId) {
    return this.list(new LambdaQueryWrapper<AdminUserRole>().eq(AdminUserRole::getRoleId, roleId)).stream().map(AdminUserRole::getAdminUserId).distinct().collect(Collectors.toList());
  }
}
