package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.sys.entities.Permission;
import cc.cary.vel.core.sys.mapper.PermissionMapper;
import cc.cary.vel.core.sys.services.IPermissionService;
import cc.cary.vel.core.sys.services.IRolePermissionService;
import cc.cary.vel.core.sys.vo.PermissionVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PermissionServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
  @Autowired
  private IRolePermissionService rolePermissionService;
  @Autowired
  private RedisUtils redisUtils;

  @Override
  public List<Permission> findUserPermissionsByUserId(long userId) {
    return this.baseMapper.findUserPermissionsByUserId(userId);
  }

  @Override
  public List<Permission> findUserPermissionsByRoleId(long roleIds) {
    return this.baseMapper.findUserPermissionsByRoleId(roleIds);
  }

  @Override
  public List<Long> findUserPermIdsByRoleId(long roleIds) {
    return findUserPermissionsByRoleId(roleIds).stream().map(Permission::getId).collect(Collectors.toList());
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Permission> tree() {
    if (redisUtils.exists(RedisKeys.MENU)) {
      return (List<Permission>) redisUtils.get(RedisKeys.MENU);
    }

    List<Permission> perms = this.list();
    List<Permission> tree = getChildren(null, perms);
    redisUtils.set(RedisKeys.MENU, tree);
    return tree;
  }

  @Override
  public List<Permission> tree(long userId) {
    List<Permission> perms = this.findUserPermissionsByUserId(userId);
    return getChildren(null, perms);
  }

  @Override
  public Permission add(PermissionVo permissionVo) {
    return saveOrUpdate(permissionVo);
  }

  @Override
  public Permission update(PermissionVo permissionVo) {
    Permission perm = getById(permissionVo.getId());
    if (perm == null) {
      throw new AppException("无效ID");
    }
    if (permissionVo.getType() != Constants.MENU_LABEL && StringUtils.isBlank(permissionVo.getPerms())) {
      throw new AppException("权限标识不能为空");
    }
    if (permissionVo.getType() == Constants.MENU_NAVBAR) {
      if (StringUtils.isBlank(permissionVo.getPath())) {
        throw new AppException("url路径不能为空");
      }
      if (StringUtils.isBlank(permissionVo.getComponent())) {
        throw new AppException("组件不能为空");
      }
      if (StringUtils.isBlank(permissionVo.getTitle())) {
        throw new AppException("菜单名称不能为空");
      }
    }

    return saveOrUpdate(permissionVo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(long id) {
    Permission perm = getById(id);
    if (perm == null) {
      throw new AppException("无效ID");
    }
    List<Permission> perms = list(new LambdaQueryWrapper<Permission>().eq(Permission::getPid, id));
    if (perms.size() > 0) {
      throw new AppException("存在子菜单不能删除");
    }
    removeById(id);
    rolePermissionService.deleteByPermissionId(id);
    redisUtils.remove(RedisKeys.MENU);
  }

  private Permission saveOrUpdate(PermissionVo permissionVo) {
    if (permissionVo.getType() != Constants.MENU_LABEL) {
      Permission perm = getOne(new LambdaQueryWrapper<Permission>().eq(Permission::getPerms, permissionVo.getPerms()));
      if (perm != null) {
        if (permissionVo.getId() == 0 || permissionVo.getId() != perm.getId()) {
          throw new AppException("权限标识重复");
        }
      }
    }
    Permission permission = new Permission();
    BeanUtils.copyProperties(permissionVo, permission);
    if (permission.getType() == Constants.MENU_BUTTON) {
      permission.setTitle("");
      permission.setIcon("");
      permission.setPath("");
      permission.setComponent("");
    }

    saveOrUpdate(permission);
    redisUtils.remove(RedisKeys.MENU);

    return permission;
  }

  List<Permission> getChildren(Permission perm, List<Permission> perms) {
    perms.sort(Comparator.comparing(Permission::getSort));
    return paresTree(perm, perms);
  }

  private List<Permission> paresTree(Permission perm, List<Permission> perms) {
    List<Permission> nodes = new ArrayList<>();
    long pid = perm == null ? 0 : perm.getId();
    for (Permission p : perms) {
      if (p.getPid() == pid) {
        p.setChildren(paresTree(p, perms));
        nodes.add(p);
      }
    }
    return nodes;
  }
}
