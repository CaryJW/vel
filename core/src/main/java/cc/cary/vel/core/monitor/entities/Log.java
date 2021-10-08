package cc.cary.vel.core.monitor.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Log
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("v_log")
public class Log {
  @ExcelProperty("ID")
  @TableId(type = IdType.AUTO)
  private Long id;

  @ExcelProperty("操作用户")
  private String username;
  @ExcelProperty("操作内容")
  private String operation;
  @ExcelProperty("耗时(毫秒)")
  private long time;
  @ExcelProperty("操作方法")
  private String method;
  @ExcelProperty("方法参数")
  private String params;
  @ExcelProperty("操作者ip")
  private String ip;
  @ExcelProperty("操作地点")
  private String location;
  @ExcelProperty("日志类型")
  private int type;
  @ExcelProperty("创建时间")
  private String createTime;
}
