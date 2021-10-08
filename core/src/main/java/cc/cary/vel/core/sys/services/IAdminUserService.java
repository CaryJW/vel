package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.sys.entities.AdminUser;
import cc.cary.vel.core.sys.vo.AdminUserVo;
import cc.cary.vel.core.sys.vo.UpdatePasswordVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * IAdminUserService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface IAdminUserService extends IService<AdminUser> {
  /**
   * 根据用户名查询用户
   *
   * @param username
   * @return AdminUser
   */
  AdminUser findByUserName(String username);

  /**
   * 分页查询
   *
   * @param request
   * @param params
   * @return IPage<AdminUser>
   */
  IPage<AdminUser> findForPage(@RequestBody QueryRequest request, Map<String, Object> params);

  /**
   * 添加用户
   *
   * @param adminUserVo
   */
  void add(AdminUserVo adminUserVo);

  /**
   * 更新用户
   *
   * @param adminUserVo
   */
  void update(AdminUserVo adminUserVo);

  /**
   * 更新密码
   *
   * @param adminUserVo
   */
  void updatePasswordVo(UpdatePasswordVo adminUserVo);

  /**
   * 更新用户名
   *
   * @param id
   * @param username
   */
  void updateUsername(long id, String username);

  /**
   * 更新头像
   *
   * @param id
   * @param url
   */
  void updateAvatar(long id, String url);
}
