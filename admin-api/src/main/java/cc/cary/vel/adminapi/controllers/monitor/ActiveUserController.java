package cc.cary.vel.adminapi.controllers.monitor;

import cc.cary.vel.adminapi.authentication.ShiroHelper;
import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.monitor.entities.ActiveUser;
import cc.cary.vel.core.monitor.services.IActiveUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ActiveUserController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/active-user")
public class ActiveUserController {
  @Autowired
  private IActiveUserService activeUserService;
  @Autowired
  private ShiroHelper shiroHelper;

  @GetMapping("/list")
  @RequiresPermissions("online-user:list")
  public ResultData list(@RequestParam String username) {
    List<ActiveUser> list = activeUserService.list(username, shiroHelper.getCurrentUser().getId());
    return ResultData.ok()
        .put("list", list)
        .put("total", list.size());
  }

  @GetMapping("/kickout/{userId}")
  @RequiresPermissions("online-user:kickout")
  @Log("踢出在线用户")
  public ResultData kickout(@PathVariable long userId) {
    activeUserService.kickout(userId);
    return ResultData.ok();
  }
}
