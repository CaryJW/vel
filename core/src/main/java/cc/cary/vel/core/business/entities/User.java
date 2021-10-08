package cc.cary.vel.core.business.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * User
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("t_user")
public class User {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String username;

  private String password;

  private int status;

  private String createTime;
}
