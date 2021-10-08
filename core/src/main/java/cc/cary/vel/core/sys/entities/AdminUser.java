package cc.cary.vel.core.sys.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * AdminUser
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_admin_user")
public class AdminUser extends Model<AdminUser> {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String username;

  private String realname;

  private String avatar;

  private Integer status;

  @JSONField(serialize = false)
  private String password;

  private String loginTime;

  private String unlockTime;

  private String createTime;

  @TableField(update = "current_timestamp")
  private String updateTime;

  @TableField(exist = false)
  private List<Role> roles;
}
