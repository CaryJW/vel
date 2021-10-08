package cc.cary.vel.core.monitor.services;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.monitor.entities.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * ILogService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface ILogService extends IService<Log> {
  /**
   * 分页查询
   *
   * @param request
   * @param params
   * @return
   */
  IPage<Log> findForPage(QueryRequest request, Map<String, Object> params);

  /**
   * 异步保存操作日志
   *
   * @param point 切点
   * @param log   日志
   */
  @Async("velAsyncThreadPool")
  void saveLog(ProceedingJoinPoint point, Log log);

  /**
   * 导出excel
   *
   * @param params
   * @param response
   */
  void export(Map<String, Object> params, HttpServletResponse response);

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
  List<Log> batchData(int offset, int limit, Map<String, Object> params);
}
