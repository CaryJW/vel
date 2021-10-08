package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.services.IConfigureService;
import cc.cary.vel.core.sys.vo.PasswordStrategyVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ConfigureController
 *
 * @author Cary
 * @date 2021/9/28
 */
@RestController
@RequestMapping("/configure")
public class ConfigureController {
  @Autowired
  private IConfigureService configureService;

  @GetMapping("/get")
  public ResultData getConfigure() {
    return ResultData.ok().put("configure", configureService.getConfigure());
  }

  @GetMapping("/get-by-value/{value}")
  public ResultData getConfigureByValue(@PathVariable String value) {
    return ResultData.ok().put("configure", configureService.getConfigureByValue(value));
  }

  @PostMapping("/save-password-strategy")
  @RequiresPermissions("configure:save-password-strategy")
  public ResultData savePasswordStrategy(PasswordStrategyVo passwordStrategy) {
    configureService.savePasswordStrategy(passwordStrategy);
    return ResultData.ok();
  }
}
