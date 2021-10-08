package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * AdminUserRole
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_admin_user_role")
public class AdminUserRole extends Model<AdminUserRole> {
  private Long adminUserId;
  private Long roleId;
}
