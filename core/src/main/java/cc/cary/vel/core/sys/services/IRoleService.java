package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.sys.entities.Role;
import cc.cary.vel.core.sys.vo.RoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * IRoleService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface IRoleService extends IService<Role> {
  /**
   * 根据用户ID查询角色
   *
   * @param userId
   * @return
   */
  List<Role> findUserRoleByUserId(long userId);

  /**
   * 根据用户IDs查询角色列表
   *
   * @param userIds
   * @return
   */
  List<Role> findUserRoleByUserIds(Collection<?> userIds);

  /**
   * 分页查询
   *
   * @param request
   * @param params
   * @return
   */
  IPage<Role> findForPage(@RequestBody QueryRequest request, Map<String, Object> params);

  /**
   * 角色map
   *
   * @return
   */
  Map<Long, String> map();

  /**
   * 根据用户IDs查询角色分组
   *
   * @param ids
   * @return
   */
  Map<Long, List<Role>> findRoleGroupByUserId(Collection<?> ids);

  /**
   * 添加角色
   *
   * @param roleVo
   */
  void add(RoleVo roleVo);

  /**
   * 更新角色
   *
   * @param roleVo
   */
  void update(RoleVo roleVo);
}
