package cc.cary.vel.core.sys.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * DictType
 *
 * @author Cary
 * @date 2021/7/22
 */
@Data
@TableName("v_dict_type")
public class DictType extends Model<DictType> {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String name;

  private String type;

  private String createTime;

  @TableField(update = "current_timestamp")
  private String updateTime;
}
