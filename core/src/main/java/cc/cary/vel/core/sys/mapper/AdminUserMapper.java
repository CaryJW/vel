package cc.cary.vel.core.sys.mapper;

import cc.cary.vel.core.sys.entities.AdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * AdminUserMapper
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {
  /**
   * 根据用户名查找用户
   *
   * @param username 用户名
   * @return AdminUser
   */
  AdminUser findByUserName(String username);

  /**
   * 分页查询
   *
   * @param page
   * @param params
   * @return IPage<AdminUser>
   */
  IPage<AdminUser> findForPage(Page<?> page, @Param("params") Map<String, Object> params);
}
