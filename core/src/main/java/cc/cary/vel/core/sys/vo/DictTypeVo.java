package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DictTypeVo
 *
 * @author Cary
 * @date 2021/7/22
 */
@Data
public class DictTypeVo {
  @NotNull
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String type;
}
