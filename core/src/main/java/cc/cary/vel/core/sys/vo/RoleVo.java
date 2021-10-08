package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * RoleVo
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class RoleVo {
  @NotNull
  private long id;
  @NotBlank
  private String roleName;
  private String remarks;
  private List<Long> permIds;
}
