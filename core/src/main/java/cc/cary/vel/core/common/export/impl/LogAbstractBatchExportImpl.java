package cc.cary.vel.core.common.export.impl;

import cc.cary.vel.core.common.export.AbstractBatchExport;
import cc.cary.vel.core.common.export.IBatchExportService;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.monitor.entities.Log;
import cc.cary.vel.core.monitor.services.ILogService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 日志批量导出
 *
 * @author Cary
 * @date 2021/5/26
 */
@Service
public class LogAbstractBatchExportImpl extends AbstractBatchExport implements IBatchExportService {
  /**
   * 分批数量
   */
  private final int limit = 10000;

  @Autowired
  private ILogService logService;

  @Override
  protected int getBatchNum() {
    int count = logService.batchCount(params);
    return (int) Math.ceil((double) count / limit);
  }

  @Override
  protected void writeData(ExcelWriter excelWriter, int currentBatch) {
    WriteSheet writeSheet = EasyExcel.writerSheet(currentBatch, "页签" + currentBatch).head(Log.class).build();
    // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
    List<?> data = getData(currentBatch);
    excelWriter.write(data, writeSheet);
  }

  private List<Log> getData(int currentBatch) {
    int offset = (currentBatch - 1) * limit;
    return logService.batchData(offset, limit, params);
  }

  @Override
  public void export(Map<String, Object> params) {
    QueryUtils.parseParamsDate(params, "createTimeStart", "createTimeEnd");
    this.params = params;
    this.fileName = "系统日志.xlsx";
    super.absExport();
  }
}
