package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * UpdatePasswordVo
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class UpdatePasswordVo {
  @NotNull
  private long id;
  
  private String oldPassword;

  @NotBlank
  private String password;
}
