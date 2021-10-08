package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.services.IAdminUserService;
import cc.cary.vel.core.sys.vo.AdminUserVo;
import cc.cary.vel.core.sys.vo.UpdatePasswordVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * AdminUserController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/admin-user")
@Validated
public class AdminUserController {
  @Autowired
  private IAdminUserService adminUserService;

  @GetMapping("/list")
  @RequiresPermissions("admin-user:list")
  public ResultData list(QueryRequest request, @RequestParam Map<String, Object> params) {
    return ResultData.ok().put(adminUserService.findForPage(request, params));
  }

  @PostMapping("/add")
  @RequiresPermissions("admin-user:add")
  @Log("添加管理员用户")
  public ResultData add(@Valid AdminUserVo adminUserVo) {
    adminUserService.add(adminUserVo);
    return ResultData.ok();
  }

  @PostMapping("/update")
  @RequiresPermissions("admin-user:update")
  @Log("修改管理员用户")
  public ResultData update(@Valid AdminUserVo adminUserVo) {
    adminUserService.update(adminUserVo);
    return ResultData.ok();
  }

  @PostMapping("/own-update-password")
  public ResultData ownUpdatePassword(@Valid UpdatePasswordVo passwordVo) {
    adminUserService.updatePasswordVo(passwordVo);
    return ResultData.ok();
  }

  @PostMapping("/update-password")
  @RequiresPermissions("password:update")
  @Log("修改管理员用户密码")
  public ResultData updatePassword(@Valid UpdatePasswordVo passwordVo) {
    adminUserService.updatePasswordVo(passwordVo);
    return ResultData.ok();
  }

  @PostMapping("/update-username")
  public ResultData updateUsername(@RequestParam long id, @RequestParam String username) {
    adminUserService.updateUsername(id, username);
    return ResultData.ok();
  }

  @PostMapping("/update-avatar")
  public ResultData updateAvatar(@RequestParam long id, @RequestParam String url) {
    adminUserService.updateAvatar(id, url);
    return ResultData.ok();
  }
}
