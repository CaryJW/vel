package cc.cary.vel.core.monitor.entities;

import lombok.Data;

/**
 * ActiveUser
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class ActiveUser {
  private long userId;
  private String username;
  private int status;
  private boolean current;
  private String loginTime;
}
