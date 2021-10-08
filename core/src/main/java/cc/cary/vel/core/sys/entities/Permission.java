package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * Permission
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_permission")
public class Permission extends Model<Permission> {
  @TableId(type = IdType.AUTO)
  private Long id;

  private Long pid;

  private String icon;

  private String name;

  private String title;

  private String component;

  private String path;

  private String perms;

  private int type;

  private int sort;

  private String createTime;

  @TableField(update = "current_timestamp")
  private String updateTime;

  @TableField(exist = false)
  List<Permission> children;
}
