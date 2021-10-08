package cc.cary.vel.adminapi.controllers.business;

import cc.cary.vel.core.common.libs.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * CronController
 *
 * @author Cary
 * @date 2021/8/30
 */
@RestController
@RequestMapping("/cron")
@Slf4j
public class CronController {
  @Value("${sys.cron-secret}")
  private String cronSecret;

  @GetMapping("/hello-shell")
  public ResultData helloShell(@RequestParam String secret) {
    if (!cronSecret.equals(secret)) {
      return ResultData.fail("secret错误");
    }
    log.info("【hello-shell】 执行。。。");
    return ResultData.ok();
  }
}
