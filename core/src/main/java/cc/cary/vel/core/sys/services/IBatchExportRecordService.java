package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.sys.entities.BatchExportRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 批量导出记录表 服务类
 *
 * @author cary
 * @date 2021-05-26
 */
public interface IBatchExportRecordService extends IService<BatchExportRecord> {
  /**
   * 下载文件
   *
   * @param id
   * @param response
   */
  void download(long id, HttpServletResponse response);

  /**
   * 获取任务进度
   *
   * @param userId
   * @param ids
   * @return
   */
  List<BatchExportRecord> progress(long userId, List<Long> ids);

  /**
   * 删除任务
   *
   * @param id
   */
  void delete(long id);

  /**
   * 取消任务
   *
   * @param id
   */
  void cancel(long id);
}
