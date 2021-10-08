package cc.cary.vel.core.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * PermissionVo
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class PermissionVo {
  @NotNull
  private long id;

  @NotNull
  private long pid;

  private String icon;

  @NotBlank
  private String name;

  private String title;

  private String component;

  private String path;

  private String perms;

  @NotNull
  private int type;

  private int sort;
}
