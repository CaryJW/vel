package cc.cary.vel.core.common.authentication;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class JwtToken implements AuthenticationToken {
  private String token;

  public JwtToken(String token) {
    this.token = token;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
