package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.sys.entities.AdminUser;
import cc.cary.vel.core.sys.entities.AdminUserRole;
import cc.cary.vel.core.sys.entities.Role;
import cc.cary.vel.core.sys.mapper.AdminUserMapper;
import cc.cary.vel.core.sys.services.IAdminUserRoleService;
import cc.cary.vel.core.sys.services.IAdminUserService;
import cc.cary.vel.core.sys.services.IRoleService;
import cc.cary.vel.core.sys.vo.AdminUserVo;
import cc.cary.vel.core.sys.vo.UpdatePasswordVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AdminUserServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {
  @Autowired
  private IAdminUserRoleService adminUserRoleService;
  @Autowired
  private IRoleService roleService;
  @Autowired
  private RedisUtils redisUtils;

  @Override
  public AdminUser findByUserName(String username) {
    return this.baseMapper.findByUserName(username);
  }

  @Override
  public IPage<AdminUser> findForPage(QueryRequest request, Map<String, Object> params) {
    IPage<AdminUser> page = this.baseMapper.findForPage(QueryUtils.buildPage(request, true), params);

    List<Long> ids = page.getRecords().stream().map(AdminUser::getId).collect(Collectors.toList());

    if (ids.size() > 0) {
      Map<Long, List<Role>> rolesMap = roleService.findRoleGroupByUserId(ids);
      for (AdminUser au : page.getRecords()) {
        au.setRoles(rolesMap.get(au.getId()));
      }
    }

    return page;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(AdminUserVo adminUserVo) {
    AdminUser u = this.findByUserName(adminUserVo.getUsername());
    if (u != null) {
      throw new AppException("用户名重复");
    }
    adminUserVo.setPassword(DigestUtils.md5DigestAsHex(adminUserVo.getPassword().getBytes()));

    AdminUser adminUser = new AdminUser();
    BeanUtils.copyProperties(adminUserVo, adminUser);
    this.save(adminUser);
    this.saveRole(adminUser.getId(), adminUserVo.getRoleIds());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(AdminUserVo adminUserVo) {
    AdminUser adminUser = new AdminUser();
    BeanUtils.copyProperties(adminUserVo, adminUser, "password");
    this.baseMapper.updateById(adminUser);

    this.saveRole(adminUser.getId(), adminUserVo.getRoleIds());

    clearCache(adminUser.getId());
  }

  @Override
  public void updatePasswordVo(UpdatePasswordVo adminUserVo) {
    AdminUser adminUser = this.getById(adminUserVo.getId());
    if (adminUser == null) {
      throw new AppException("无效ID");
    }
    String newPw = DigestUtils.md5DigestAsHex(adminUserVo.getPassword().getBytes());
    if (StringUtils.isNoneEmpty(adminUserVo.getOldPassword())) {
      String oldPw = DigestUtils.md5DigestAsHex(adminUserVo.getOldPassword().getBytes());
      if (!adminUser.getPassword().equals(oldPw)) {
        throw new AppException("原密码错误");
      }
    }
    adminUser.setPassword(newPw);
    this.updateById(adminUser);

    clearCache(adminUser.getId());
  }

  @Override
  public void updateUsername(long id, String username) {
    AdminUser adminUser = this.getById(id);
    if (adminUser == null) {
      throw new AppException("无效ID");
    }
    adminUser.setUsername(username);
    this.updateById(adminUser);
  }

  @Override
  public void updateAvatar(long id, String url) {
    AdminUser adminUser = this.getById(id);
    if (adminUser == null) {
      throw new AppException("无效ID");
    }
    adminUser.setAvatar(url);
    this.updateById(adminUser);
  }

  /**
   * 删除权限和角色权限缓存
   *
   * @param userId
   */
  private void clearCache(long userId) {
    redisUtils.hmRemove(RedisKeys.USER_ROLES, String.valueOf(userId));
    redisUtils.hmRemove(RedisKeys.USER_PERMISSIONS, String.valueOf(userId));
  }

  private void saveRole(long userId, List<Long> roleIds) {
    adminUserRoleService.deleteRoleByAdminUserId(userId);
    if (roleIds != null && roleIds.size() > 0) {
      List<AdminUserRole> userRoles = new ArrayList<>();
      for (long roleId : roleIds) {
        AdminUserRole ur = new AdminUserRole();
        ur.setAdminUserId(userId);
        ur.setRoleId(roleId);
        userRoles.add(ur);
      }
      adminUserRoleService.saveBatch(userRoles);
    }
  }
}
