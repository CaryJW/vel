package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.adminapi.authentication.ShiroHelper;
import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.services.IPermissionService;
import cc.cary.vel.core.sys.vo.PermissionVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * PermissionController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {
  @Autowired
  private IPermissionService permissionService;
  @Autowired
  private ShiroHelper shiroHelper;

  @GetMapping("/current-user")
  public ResultData currentUser() {
    return ResultData.ok().put("tree", permissionService.tree(shiroHelper.getCurrentUser().getId()));
  }

  @GetMapping("/tree")
  public ResultData tree() {
    return ResultData.ok().put("tree", permissionService.tree());
  }

  @PostMapping("/add")
  @Log("添加权限")
  @RequiresPermissions("menu:add")
  public ResultData add(@Valid PermissionVo permissionVo) {
    return ResultData.ok().put("permission", permissionService.add(permissionVo));
  }

  @PostMapping("/update")
  @Log("更新权限")
  @RequiresPermissions("menu:update")
  public ResultData update(@Valid PermissionVo permissionVo) {
    return ResultData.ok().put("permission", permissionService.update(permissionVo));
  }

  @GetMapping("/delete/{id}")
  @Log("删除权限")
  @RequiresPermissions("menu:delete")
  public ResultData update(@PathVariable long id) {
    permissionService.delete(id);
    return ResultData.ok();
  }

  @GetMapping("/role/{id}")
  public ResultData role(@PathVariable long id) {
    return ResultData.ok().put("permIds", permissionService.findUserPermIdsByRoleId(id));
  }
}
