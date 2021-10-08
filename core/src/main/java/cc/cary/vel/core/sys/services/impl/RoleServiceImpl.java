package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.sys.entities.Role;
import cc.cary.vel.core.sys.entities.RolePermission;
import cc.cary.vel.core.sys.mapper.RoleMapper;
import cc.cary.vel.core.sys.services.IAdminUserRoleService;
import cc.cary.vel.core.sys.services.IRolePermissionService;
import cc.cary.vel.core.sys.services.IRoleService;
import cc.cary.vel.core.sys.vo.RoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RoleServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
  @Autowired
  private IRolePermissionService rolePermissionService;
  @Autowired
  private IAdminUserRoleService adminUserRoleService;
  @Autowired
  private RedisUtils redisUtils;

  @Override
  public List<Role> findUserRoleByUserId(long userId) {
    return this.baseMapper.findUserRoleByUserId(userId);
  }

  @Override
  public List<Role> findUserRoleByUserIds(Collection<?> userIds) {
    return this.baseMapper.findUserRoleByUserIds(userIds);
  }

  @Override
  public IPage<Role> findForPage(QueryRequest request, Map<String, Object> params) {
    return this.baseMapper.findForPage(QueryUtils.buildPage(request, true), params);
  }

  @Override
  public Map<Long, String> map() {
    return this.list().stream().collect(Collectors.toMap(Role::getId, Role::getRoleName));
  }

  @Override
  public Map<Long, List<Role>> findRoleGroupByUserId(Collection<?> ids) {
    return this.findUserRoleByUserIds(ids).stream().collect(Collectors.groupingBy(Role::getAdminUserId));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(RoleVo roleVo) {
    Role role = new Role();
    BeanUtils.copyProperties(roleVo, role);
    this.save(role);
    this.savePermissions(role, roleVo.getPermIds());
  }

  @Override
  public void update(RoleVo roleVo) {
    Role role = this.getById(roleVo.getId());
    if (role == null) {
      throw new AppException("无效ID");
    }
    BeanUtils.copyProperties(roleVo, role);
    this.updateById(role);
    this.savePermissions(role, roleVo.getPermIds());
  }

  private void savePermissions(Role role, List<Long> permIds) {
    rolePermissionService.deleteByRoleId(role.getId());

    if (permIds != null && permIds.size() > 0) {
      List<RolePermission> rps = new ArrayList<>();
      for (long permId : permIds) {
        RolePermission rp = new RolePermission();
        rp.setRoleId(role.getId());
        rp.setPermissionId(permId);
        rps.add(rp);
      }
      rolePermissionService.saveBatch(rps);
    }
    // 和该角色关联的用户需要删除角色和权限缓存
    List<Long> userIds = adminUserRoleService.getAdminUserIdsByRoleId(role.getId());
    clearCache(userIds);
  }

  /**
   * 删除用户登录、权限和角色权限
   *
   * @param userIds
   */
  private void clearCache(List<Long> userIds) {
    List<String> ids = userIds.stream().map(String::valueOf).collect(Collectors.toList());
    redisUtils.hmRemove(RedisKeys.USER_ROLES, ids.toArray());
    redisUtils.hmRemove(RedisKeys.USER_PERMISSIONS, ids.toArray());
  }
}
