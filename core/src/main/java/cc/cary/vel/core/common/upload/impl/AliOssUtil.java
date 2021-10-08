package cc.cary.vel.core.common.upload.impl;

import cc.cary.vel.core.common.entities.UploadResult;
import cc.cary.vel.core.common.properties.UploadProperties;
import cc.cary.vel.core.common.upload.UploadUtil;
import cc.cary.vel.core.common.utils.VelFileUtils;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * 阿里云oss上传
 *
 * @author Cary
 * @date 2021/05/22
 */
@Component
@Slf4j
public class AliOssUtil implements UploadUtil {
  @Autowired
  private UploadProperties uploadProperties;

  private UploadProperties.AliConfig config;
  private OSS ossClient;

  @PostConstruct
  public void init() {
    this.config = uploadProperties.getAli();
    this.ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
  }

  @Override
  public String type() {
    return "ali";
  }

  @Override
  public UploadResult upload(MultipartFile file) throws IOException {
    return this.upload(Objects.requireNonNull(VelFileUtils.multipartFileToFile(file)));
  }

  @Override
  public UploadResult upload(File file) throws IOException {
    String extName = file.getName().substring(file.getName().lastIndexOf("."));
    String fileName = UUID.randomUUID() + extName;
    return this.upload(file, fileName);
  }

  @Override
  public UploadResult upload(File file, String fileName) throws IOException {
    try {
      ossClient.putObject(config.getBucketName(), config.getFilePath() + fileName, file);
    } catch (Exception e) {
      log.error("图片上传失败: {}", e.getMessage());
      throw e;
    } finally {
      ossClient.shutdown();
      file.delete();
    }
    UploadResult uploadResult = new UploadResult();
    uploadResult.setFileName(fileName);
    uploadResult.setUrl(config.getPublicBucketName() + "/" + config.getFilePath() + fileName);
    return uploadResult;
  }

  @Override
  public UploadResult uploadBase64(String base64) {
    return null;
  }

  @Override
  public Boolean delete(String url) {
    String filePath = url.replace(config.getPublicBucketName() + "/", "");
    try {
      ossClient.deleteObject(config.getBucketName(), filePath);
    } catch (OSSException | ClientException e) {
      log.error("图片删除失败: {}", e.getMessage());
      return false;
    } finally {
      // 关闭OSSClient
      ossClient.shutdown();
    }
    return true;
  }
}
