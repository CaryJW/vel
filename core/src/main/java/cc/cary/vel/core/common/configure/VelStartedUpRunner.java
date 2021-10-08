package cc.cary.vel.core.common.configure;

import cc.cary.vel.core.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 启动后运行
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
@Component
public class VelStartedUpRunner implements ApplicationRunner {
  @Autowired
  private ConfigurableApplicationContext context;
  @Autowired
  private RedisUtils redisUtils;

  @Value("${server.port}")
  private String port;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try {
      redisUtils.exists("test_key");
    } catch (Exception e) {
      log.error(" ____   __    _   _ ");
      log.error("| |_   / /\\  | | | |");
      log.error("|_|   /_/--\\ |_| |_|__");
      log.error("                        ");
      log.error("Vel启动失败，{}", e.getMessage());
      log.error("Redis连接异常，请检查Redis连接配置并确保Redis服务已启动");
      // 关闭 FEBS
      context.close();
    }
    if (context.isActive()) {
      InetAddress address = InetAddress.getLocalHost();
      String url = String.format("http://%s:%s", address.getHostAddress(), port);

      log.info(" __    ___   _      ___   _     ____ _____  ____ ");
      log.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
      log.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
      log.info("                                                      ");
      log.info("vel启动完毕，环境：{}，本地url：{}", context.getEnvironment().getActiveProfiles()[0], url);
    }
  }
}
