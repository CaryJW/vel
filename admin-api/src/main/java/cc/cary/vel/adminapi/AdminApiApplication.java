package cc.cary.vel.adminapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动器
 *
 * @author Cary
 * @date 2021/05/22
 */
@SpringBootApplication
@MapperScan("cc.cary.vel.core.*.mapper")
@ComponentScan(basePackages = {"cc.cary.vel.adminapi", "cc.cary.vel.core"})
@EnableAsync
public class AdminApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdminApiApplication.class, args);
  }

}
