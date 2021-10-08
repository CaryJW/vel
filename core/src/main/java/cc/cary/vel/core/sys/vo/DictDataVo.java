package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DictDataVo
 *
 * @author Cary
 * @date 2021/7/22
 */
@Data
public class DictDataVo {
  @NotNull
  private Long id;

  @NotNull
  private Integer sort;

  @NotBlank
  private String label;

  @NotBlank
  private String value;

  @NotBlank
  private String type;

  @NotNull
  private Integer status;

  private String extend;
}
