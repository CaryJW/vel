package cc.cary.vel.serviceapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动器
 *
 * @author Cary
 * @date 2021/05/22
 */
@SpringBootApplication
@MapperScan("cc.cary.vel.core.*.mapper")
@ComponentScan(basePackages = {"cc.cary.vel.serviceapi", "cc.cary.vel.core"})
public class ServiceApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceApiApplication.class, args);
  }

}
