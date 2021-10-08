package cc.cary.vel.core.common.upload.impl;

import cc.cary.vel.core.common.entities.UploadResult;
import cc.cary.vel.core.common.properties.UploadProperties;
import cc.cary.vel.core.common.upload.UploadUtil;
import cc.cary.vel.core.common.utils.VelDateUtils;
import cc.cary.vel.core.common.utils.VelFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 本地上传
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
@Slf4j
public class LocalUploadUtil implements UploadUtil {
  @Autowired
  private UploadProperties uploadProperties;

  @Override
  public String type() {
    return "local";
  }

  @Override
  public UploadResult upload(MultipartFile file) throws IOException {
    return upload(Objects.requireNonNull(VelFileUtils.multipartFileToFile(file)));
  }

  @Override
  public UploadResult upload(File file) throws IOException {
    String extName = file.getName().substring(file.getName().lastIndexOf("."));
    String fileName = UUID.randomUUID() + extName;
    return this.upload(file, fileName);
  }

  @Override
  public UploadResult upload(File file, String fileName) throws IOException {
    UploadProperties.LocalConfig config = uploadProperties.getLocal();
    String date = VelDateUtils.formatFullTime(LocalDateTime.now(), VelDateUtils.FULL_DATE_PATTERN);
    // 保存文件
    try {
      // 设置文件存储路径
      File targetFile = new File(config.getFilePath() + date + "/" + fileName);
      org.apache.commons.io.FileUtils.copyFile(file, targetFile);
    } catch (IOException e) {
      log.error("文件上传失败: {}", e.getMessage());
      throw e;
    } finally {
      file.delete();
    }
    UploadResult uploadResult = new UploadResult();
    uploadResult.setFileName(fileName);
    uploadResult.setUrl(config.getLocalhost() + "/" + date + "/" + fileName);
    return uploadResult;
  }

  @Override
  public UploadResult uploadBase64(String base64) {
    return null;
  }

  @Override
  public Boolean delete(String url) {
    UploadProperties.LocalConfig config = uploadProperties.getLocal();

    String filePath = config.getFilePath() + url.replace(config.getLocalhost() + "/", "");
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
      return true;
    }
    return false;
  }
}
