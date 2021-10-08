package cc.cary.vel.core.common.upload;

import cc.cary.vel.core.common.entities.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface UploadUtil {
  /**
   * 云存储类型
   *
   * @return
   */
  String type();

  /**
   * 文件上传
   *
   * @param file
   * @return
   * @throws IOException
   */
  UploadResult upload(MultipartFile file) throws IOException;

  /**
   * 文件上传
   *
   * @param file
   * @return
   * @throws IOException
   */
  UploadResult upload(File file) throws IOException;

  /**
   * 文件上传
   *
   * @param file
   * @param fileName
   * @return
   * @throws IOException
   */
  UploadResult upload(File file, String fileName) throws IOException;

  /**
   * 上传base64格式的文件
   *
   * @param base64 文件的base64编码
   * @return
   */
  UploadResult uploadBase64(String base64);

  /**
   * 删除文件
   *
   * @param url 文件url
   * @return
   */
  Boolean delete(String url);
}
