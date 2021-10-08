package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * Role
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_role")
public class Role extends Model<Role> {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String roleName;

  private String remarks;

  private String createTime;

  @TableField(update = "current_timestamp")
  private String updateTime;

  @TableField(exist = false)
  private Long adminUserId;

  @TableField(exist = false)
  private List<Long> permIds;
}
