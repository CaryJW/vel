package cc.cary.vel.core.monitor.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * LoginLog
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_login_log")
public class LoginLog {
  @ExcelProperty("ID")
  @TableId(type = IdType.AUTO)
  private Long id;

  @ExcelProperty("操作用户")
  private String username;
  @ExcelProperty("登录地点")
  private String location;
  @ExcelProperty("IP")
  private String ip;
  @ExcelProperty("操作系统")
  @TableField(value = "`system`")
  private String system;
  @ExcelProperty("浏览器")
  private String browser;
  @ExcelProperty("登录时间")
  private String loginTime;
}
