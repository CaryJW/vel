package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * AdminUserVo
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class AdminUserVo {
  @NotNull
  private Long id;

  @NotBlank
  private String username;

  @NotBlank
  private String realname;

  private String avatar;

  @NotNull
  private Integer status;

  private String password;

  private List<Long> roleIds;
}
