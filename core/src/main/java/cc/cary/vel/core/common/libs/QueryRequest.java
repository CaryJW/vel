package cc.cary.vel.core.common.libs;

import lombok.Data;

/**
 * 分页请求信息
 *
 * @author Cary
 */
@Data
public class QueryRequest {
  /**
   * 当前页码
   */
  private int page = 1;

  /**
   * 当前页面数据量
   */
  private int limit = 10;

  /**
   * 排序
   */
  private String sort = "+id";
}
