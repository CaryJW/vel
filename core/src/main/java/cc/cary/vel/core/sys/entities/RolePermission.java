package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * RolePermission
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_role_permission")
public class RolePermission {
  private Long roleId;
  private Long permissionId;
}
