package cc.cary.vel.core.monitor.mapper;

import cc.cary.vel.core.monitor.entities.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * LoginLogMapper
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {
  /**
   * 分页查询
   *
   * @param page
   * @param params
   * @return
   */
  IPage<LoginLog> findForPage(Page<?> page, @Param("params") Map<String, Object> params);

  /**
   * 查询批量总数
   *
   * @param params
   * @return
   */
  int batchCount(@Param("params") Map<String, Object> params);

  /**
   * 查询分批数据
   *
   * @param offset
   * @param limit
   * @param params
   * @return
   */
  List<LoginLog> batchData(@Param("offset") int offset, @Param("limit") int limit, @Param("params") Map<String, Object> params);
}
