package cc.cary.vel.adminapi.controllers.monitor;

import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.export.IBatchExportService;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.monitor.services.ILogService;
import cc.cary.vel.core.sys.services.IBatchExportRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * LogController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/log")
public class LogController {
  @Autowired
  private ILogService logService;
  @Autowired
  @Qualifier("logAbstractBatchExportImpl")
  private IBatchExportService batchExportService;
  @Autowired
  private IBatchExportRecordService batchExportRecordService;

  @GetMapping("/list")
  @RequiresPermissions("sys-log:list")
  public ResultData list(QueryRequest request, @RequestParam Map<String, Object> params) {
    return ResultData.ok().put(logService.findForPage(request, params));
  }

  @GetMapping("/delete/{id}")
  @Log("删除系统日志")
  @RequiresPermissions("sys-log:delete")
  public ResultData delete(@PathVariable long id) {
    logService.removeById(id);
    return ResultData.ok();
  }

  @GetMapping("/export")
  @Log("导出系统日志")
  @RequiresPermissions("sys-log:export")
  public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) {
    logService.export(params, response);
  }

  @GetMapping("/batch-export")
  @Log("批量导出系统日志")
  @RequiresPermissions("sys-log:batch-export")
  public ResultData batchExport(@RequestParam Map<String, Object> params) {
    batchExportService.export(params);
    return ResultData.ok();
  }
}
