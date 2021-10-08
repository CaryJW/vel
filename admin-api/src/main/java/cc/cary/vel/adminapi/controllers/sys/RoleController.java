package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.services.IRoleService;
import cc.cary.vel.core.sys.vo.RoleVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * RoleController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
  @Autowired
  private IRoleService roleService;

  @GetMapping("/list")
  @RequiresPermissions("role:list")
  public ResultData list(QueryRequest request, @RequestParam Map<String, Object> params) {
    return ResultData.ok().put(roleService.findForPage(request, params));
  }

  @GetMapping("/map")
  public ResultData map() {
    return ResultData.ok().put("roleMap", roleService.map());
  }

  @PostMapping("/add")
  @Log("添加角色")
  @RequiresPermissions("role:add")
  public ResultData add(@Valid RoleVo roleVo) {
    roleService.add(roleVo);
    return ResultData.ok();
  }

  @PostMapping("/update")
  @Log("更新角色")
  @RequiresPermissions("role:update")
  public ResultData update(@Valid RoleVo roleVo) {
    roleService.update(roleVo);
    return ResultData.ok();
  }
}
