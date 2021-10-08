package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.adminapi.authentication.ShiroHelper;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.entities.BatchExportRecord;
import cc.cary.vel.core.sys.services.IBatchExportRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 批量导出记录表 前端控制器
 *
 * @author cary
 * @since 2021-05-26
 */
@RestController
@RequestMapping("/task")
public class TaskController {
  @Autowired
  private IBatchExportRecordService batchExportRecordService;
  @Autowired
  private ShiroHelper shiroHelper;

  @GetMapping("/list")
  @RequiresPermissions("task:list")
  public ResultData list() {
    List<BatchExportRecord> list = batchExportRecordService.list(new LambdaQueryWrapper<BatchExportRecord>()
        .eq(BatchExportRecord::getUserId, shiroHelper.getCurrentUser().getId())
        .ne(BatchExportRecord::getStatus, Constants.FILE_EXPORT_STATUS_DELETED)
        .orderByDesc(BatchExportRecord::getId));
    return ResultData.ok().put("list", list);
  }

  @GetMapping("/download/{id}")
  @RequiresPermissions("task:download")
  public void download(@PathVariable long id, HttpServletResponse response) {
    batchExportRecordService.download(id, response);
  }

  @GetMapping("/progress")
  public ResultData progress(@RequestParam List<Long> ids) {
    return ResultData.ok().put("tasks", batchExportRecordService.progress(shiroHelper.getCurrentUser().getId(), ids));
  }

  @GetMapping("/delete/{id}")
  @RequiresPermissions("task:delete")
  public ResultData delete(@PathVariable long id) {
    batchExportRecordService.delete(id);
    return ResultData.ok();
  }

  @GetMapping("/cancel/{id}")
  @RequiresPermissions("task:cancel")
  public ResultData cancel(@PathVariable long id) {
    batchExportRecordService.cancel(id);
    return ResultData.ok();
  }
}
