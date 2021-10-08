package cc.cary.vel.core.business.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Tinymce
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
@TableName("t_tinymce")
public class Tinymce {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String content;
}
