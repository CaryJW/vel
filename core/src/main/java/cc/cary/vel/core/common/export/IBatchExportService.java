package cc.cary.vel.core.common.export;

import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 批量导出接口
 *
 * @author Cary
 * @date 2021/5/26
 */
public interface IBatchExportService {
  /**
   * 批量异步导出
   *
   * @param params
   */
  @Async("velAsyncThreadPool")
  void export(Map<String, Object> params);
}
