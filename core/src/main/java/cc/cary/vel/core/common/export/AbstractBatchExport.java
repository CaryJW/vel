package cc.cary.vel.core.common.export;

import cc.cary.vel.core.common.entities.UploadResult;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.upload.UploadUtil;
import cc.cary.vel.core.common.utils.VelFileUtils;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.sys.entities.AdminUser;
import cc.cary.vel.core.sys.entities.BatchExportRecord;
import cc.cary.vel.core.sys.services.IBatchExportRecordService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 批量导出抽象类
 *
 * @author Cary
 * @date 2021/5/26
 */
@Slf4j
public abstract class AbstractBatchExport {
  /**
   * 文件名称
   */
  protected String fileName;
  /**
   * 查询条件
   */
  protected Map<String, Object> params;
  @Autowired
  private UploadUtil uploadUtil;
  @Autowired
  private IBatchExportRecordService batchExportRecordService;
  @Autowired
  private RedisUtils redisUtils;

  /**
   * 获取批次
   *
   * @return
   */
  protected abstract int getBatchNum();

  /**
   * 写入数据
   *
   * @param excelWriter
   * @param currentBatch
   */
  protected abstract void writeData(ExcelWriter excelWriter, int currentBatch);


  /**
   * 导出操作
   */
  public void absExport() {
    Object user = SecurityUtils.getSubject().getPrincipal();
    AdminUser adminUser;
    if (user instanceof AdminUser) {
      adminUser = (AdminUser) user;
    } else {
      log.error("批量导出失败: 获取登录用户信息失败");
      return;
    }

    long beginTime = System.currentTimeMillis();

    // 保存记录
    BatchExportRecord exportRecord = new BatchExportRecord();
    exportRecord.setUserId(adminUser.getId());
    exportRecord.setUsername(adminUser.getUsername());
    exportRecord.setFileName(fileName);
    exportRecord.setStatus(Constants.FILE_EXPORT_STATUS_NORMAL);
    batchExportRecordService.save(exportRecord);

    ExcelWriter excelWriter = null;
    File file = null;
    try {
      //
      int batchNum = getBatchNum();
      file = File.createTempFile("temp_", ".xlsx");
      excelWriter = EasyExcel.write(file).build();

      for (int i = 1; i <= batchNum; i++) {
        // 检查任务是否已经被取消
        String key = RedisKeys.BATCH_EXPORT_CANCEL + exportRecord.getId();
        if (redisUtils.exists(key)) {
          exportRecord.setStatus(Constants.FILE_EXPORT_STATUS_CANCEL);
          redisUtils.remove(key);
          break;
        }

        // 写入数据
        writeData(excelWriter, i);
        // 保存进度
        saveProgress(adminUser.getId(), exportRecord.getId(), batchNum, i);
      }
    } catch (IOException ioe) {
      log.error("批量导出失败：{}", ioe.getMessage());
      exportRecord.setStatus(Constants.FILE_EXPORT_STATUS_FAIL);
      exportRecord.setException(ioe.getMessage());
    } finally {
      if (excelWriter != null) {
        excelWriter.finish();
      }
    }

    if (file != null && exportRecord.getStatus() != Constants.FILE_EXPORT_STATUS_CANCEL) {
      try {
        // 上传文件
        UploadResult uploadResult = uploadFile(file, exportRecord);

        exportRecord.setStorageFileName(uploadResult.getFileName());
        exportRecord.setUrl(uploadResult.getUrl());
        exportRecord.setStatus(Constants.FILE_EXPORT_STATUS_COMPLETE);
      } catch (Exception e) {
        exportRecord.setStatus(Constants.FILE_EXPORT_STATUS_FAIL);
        exportRecord.setException(e.getMessage());
      }
      long time = System.currentTimeMillis() - beginTime;
      exportRecord.setTime(time);
    }

    batchExportRecordService.updateById(exportRecord);
    // 删除进度
    deleteProgress(adminUser.getId(), exportRecord.getId());
  }

  private UploadResult uploadFile(File file, BatchExportRecord exportRecord) throws IOException {
    // 10MB
    long tenMb = 1024 * 1024;
    long fileSize = file.length();
    if (fileSize >= tenMb) {
      // 压缩
      File zip = File.createTempFile("temp_", ".zip");
      VelFileUtils.compress(file, zip);

      // 重新设置文件名
      String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".zip";
      exportRecord.setFileName(newFileName);
      // 设置文件大小
      exportRecord.setFileSize(VelFileUtils.formatFileSize(zip.length()));
      file.delete();
      return uploadUtil.upload(zip);
    } else {
      exportRecord.setFileSize(VelFileUtils.formatFileSize(fileSize));
      return uploadUtil.upload(file);
    }
  }

  private void saveProgress(long userId, long recordId, int batchNum, int current) {
    String key = RedisKeys.BATCH_EXPORT_PREFIX + userId + "_" + recordId;
    int progress = (int) ((double) current / batchNum * 100);
    redisUtils.set(key, progress);
  }

  private void deleteProgress(long userId, long recordId) {
    String key = RedisKeys.BATCH_EXPORT_PREFIX + userId + "_" + recordId;
    redisUtils.remove(key);
  }
}
