package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.upload.UploadUtil;
import cc.cary.vel.core.common.utils.VelFileUtils;
import cc.cary.vel.core.common.utils.RedisUtils;
import cc.cary.vel.core.sys.entities.BatchExportRecord;
import cc.cary.vel.core.sys.mapper.BatchExportRecordMapper;
import cc.cary.vel.core.sys.services.IBatchExportRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 批量导出记录表 服务实现类
 *
 * @author cary
 * @date 2021-05-26
 */
@Service
@Slf4j
public class BatchExportRecordServiceImpl extends ServiceImpl<BatchExportRecordMapper, BatchExportRecord> implements IBatchExportRecordService {
  @Autowired
  private RedisUtils redisUtils;
  @Autowired
  private UploadUtil uploadUtil;

  @Override
  public void download(long id, HttpServletResponse response) {
    BatchExportRecord record = getById(id);
    if (record == null) {
      throw new AppException("无效ID");
    }
    try {
      VelFileUtils.download(record.getUrl(), record.getFileName(), true, response);
    } catch (Exception e) {
      log.error("文件下载失败：{}", e.getMessage());
      throw new AppException("文件下载失败");
    }
  }

  @Override
  public List<BatchExportRecord> progress(long userId, List<Long> ids) {
    List<BatchExportRecord> records = listByIds(ids);
    String key;
    for (BatchExportRecord record : records) {
      key = RedisKeys.BATCH_EXPORT_PREFIX + userId + "_" + record.getId();
      if (redisUtils.exists(key)) {
        record.setProgress((int) redisUtils.get(key));
      } else {
        record.setProgress(0);
      }
    }
    return records;
  }

  @Override
  public void delete(long id) {
    BatchExportRecord record = getById(id);
    if (record == null) {
      throw new AppException("无效ID");
    }
    record.setStatus(Constants.FILE_EXPORT_STATUS_DELETED);
    updateById(record);

    uploadUtil.delete(record.getUrl());
  }

  @Override
  public void cancel(long id) {
    BatchExportRecord record = getById(id);
    if (record == null) {
      throw new AppException("无效ID");
    }
    redisUtils.set(RedisKeys.BATCH_EXPORT_CANCEL + id, Constants.FILE_EXPORT_STATUS_CANCEL);
  }
}
