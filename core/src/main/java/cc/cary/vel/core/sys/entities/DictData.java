package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * DictData
 *
 * @author Cary
 * @date 2021/7/22
 */
@Data
@TableName("v_dict_data")
public class DictData extends Model<DictData> {
  @TableId(type = IdType.AUTO)
  private Long id;

  private Integer sort;

  private String label;

  private String value;

  private String type;

  private Integer status;

  private String extend;

  private String createTime;

  @TableField(update = "current_timestamp")
  private String updateTime;
}