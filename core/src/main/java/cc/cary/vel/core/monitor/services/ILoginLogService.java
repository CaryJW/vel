package cc.cary.vel.core.monitor.services;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.monitor.entities.LoginLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * ILoginLogService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface ILoginLogService extends IService<LoginLog> {
  /**
   * 分页查询
   *
   * @param request
   * @param params
   * @return IPage<LoginLog>
   */
  IPage<LoginLog> findForPage(QueryRequest request, Map<String, Object> params);

  /**
   * 查询批量总数
   *
   * @param params
   * @return
   */
  int batchCount(Map<String, Object> params);

  /**
   * 查询分批数据
   *
   * @param offset
   * @param limit
   * @param params
   * @return
   */
  List<LoginLog> batchData(int offset, int limit, Map<String, Object> params);
}
