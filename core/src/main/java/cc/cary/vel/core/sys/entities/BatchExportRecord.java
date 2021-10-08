package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 批量导出记录表
 *
 * @author cary
 * @date 2021-05-26
 */
@Data
@TableName("v_batch_export_record")
public class BatchExportRecord extends Model<BatchExportRecord> {
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 操作用户ID
   */
  private Long userId;

  /**
   * 操作用户
   */
  private String username;

  /**
   * 文件名
   */
  private String fileName;

  /**
   * 存储的文件名
   */
  private String storageFileName;

  /**
   * 文件大小
   */
  private String fileSize;

  /**
   * 下载地址
   */
  private String url;

  /**
   * 耗时(毫秒)
   */
  private Long time;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 异常原因
   */
  private String exception;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 任务进度
   */
  @TableField(exist = false)
  private int progress;
}
