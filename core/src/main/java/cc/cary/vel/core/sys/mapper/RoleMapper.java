package cc.cary.vel.core.sys.mapper;

import cc.cary.vel.core.sys.entities.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * RoleMapper
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface RoleMapper extends BaseMapper<Role> {
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
   * @param userId
   * @return
   */
  List<Role> findUserRoleByUserIds(Collection<?> userId);

  /**
   * 分页查询
   *
   * @param page
   * @param params
   * @return
   */
  IPage<Role> findForPage(Page<Role> page, @Param("params") Map<String, Object> params);
}
