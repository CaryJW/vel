package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * LoginVo
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class LoginVo {
  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String key;
  @NotBlank
  private String captcha;
}
