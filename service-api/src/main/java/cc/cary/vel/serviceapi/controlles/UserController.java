package cc.cary.vel.serviceapi.controlles;

import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.services.ILoginService;
import cc.cary.vel.serviceapi.authentication.ShiroHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
public class UserController {
  @Autowired
  private ILoginService loginService;
  @Autowired
  private ShiroHelper shiroHelper;

  @PostMapping("/login")
  public ResultData login(@RequestParam String username,
                          @RequestParam String password) {
    return ResultData.ok().put("token", loginService.userLogin(username, password));
  }

  @GetMapping("/currentUserInfo")
  @Log(value = "api获取当前登录用户信息", type = Constants.API)
  public ResultData info() {
    return ResultData.ok()
        .put("user", shiroHelper.getCurrentUser());
  }

  @GetMapping("/logout")
  public ResultData logout() {
    shiroHelper.logout();
    return ResultData.ok();
  }

}
