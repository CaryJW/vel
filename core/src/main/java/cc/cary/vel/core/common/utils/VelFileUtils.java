package cc.cary.vel.core.common.utils;

import cc.cary.vel.core.common.entities.FileSizeType;
import cc.cary.vel.core.common.libs.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
public class VelFileUtils {
  /**
   * MultipartFile 转 File
   *
   * @param file
   * @return
   */
  public static File multipartFileToFile(MultipartFile file) {
    try {
      String extName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
      File toFile = File.createTempFile("temp_", extName);
      file.transferTo(toFile);
      return toFile;
    } catch (IOException | IllegalStateException e) {
      log.error("MultipartFile 转 File失败：{}", e.getMessage());
    }
    return null;
  }

  /**
   * 获取文件类型
   *
   * @param file 文件
   * @return 文件类型
   * @throws Exception Exception
   */
  private static String getFileType(File file) throws Exception {
    if (file.isDirectory()) {
      throw new Exception("file不是文件");
    }
    String fileName = file.getName();
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  /**
   * 文件下载
   *
   * @param filePath 待下载文件路径
   * @param fileName 下载文件名称
   * @param delete   下载后是否删除源文件
   * @param response HttpServletResponse
   * @throws Exception Exception
   */
  public static void download(String filePath, String fileName, Boolean delete, HttpServletResponse response) throws Exception {
    File file;
    if (VelUtils.isHttpUrl(filePath)) {
      File tempFile = File.createTempFile("net_file", fileName.substring(fileName.lastIndexOf(".")));
      FileUtils.copyURLToFile(new URL(filePath), tempFile);
      file = tempFile;
    } else {
      file = new File(filePath);
      if (!file.exists()) {
        throw new Exception("文件未找到");
      }
    }
    download(file, fileName, delete, response);
  }

  public static void download(File file, String fileName, Boolean delete, HttpServletResponse response) throws Exception {
    String fileType = getFileType(file);
    if (!fileTypeIsValid(fileType)) {
      throw new Exception("暂不支持该类型文件下载");
    }
    response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName,
        StandardCharsets.UTF_8.name()));
    response.setContentType("multipart/form-data");
    response.setCharacterEncoding("utf-8");
    try (InputStream inputStream = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
      byte[] b = new byte[2048];
      int length;
      while ((length = inputStream.read(b)) > 0) {
        os.write(b, 0, length);
      }
    } finally {
      if (delete) {
        file.delete();
      }
    }
  }

  /**
   * 校验文件类型是否是允许下载的类型
   * （出于安全考虑：https://github.com/wuyouzhuguli/FEBS-Shiro/issues/40）
   *
   * @param fileType fileType
   * @return Boolean
   */
  private static Boolean fileTypeIsValid(String fileType) {
    fileType = StringUtils.lowerCase(fileType);
    return ArrayUtils.contains(Constants.VALID_FILE_TYPE, fileType);
  }

  /**
   * 压缩文件或目录
   *
   * @param fromPath 待压缩文件或路径
   * @param toPath   压缩文件，如 xx.zip
   */
  public static void compress(String fromPath, String toPath) throws IOException {
    File fromFile = new File(fromPath);
    File toFile = new File(toPath);
    if (!fromFile.exists()) {
      throw new FileNotFoundException(fromPath + "不存在！");
    }
    compress(fromFile, toFile);
  }

  public static void compress(File fromFile, File toFile) throws IOException {
    try (
        FileOutputStream outputStream = new FileOutputStream(toFile);
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
        ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream)
    ) {
      String baseDir = "";
      compress(fromFile, zipOutputStream, baseDir);
    }
  }

  private static void compress(File file, ZipOutputStream zipOut, String baseDir) throws IOException {
    if (file.isDirectory()) {
      compressDirectory(file, zipOut, baseDir);
    } else {
      compressFile(file, zipOut, baseDir);
    }
  }

  private static void compressDirectory(File dir, ZipOutputStream zipOut, String baseDir) throws IOException {
    File[] files = dir.listFiles();
    if (files != null && ArrayUtils.isNotEmpty(files)) {
      for (File file : files) {
        compress(file, zipOut, baseDir + dir.getName() + "/");
      }
    }
  }

  private static void compressFile(File file, ZipOutputStream zipOut, String baseDir) throws IOException {
    if (!file.exists()) {
      return;
    }
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
      ZipEntry entry = new ZipEntry(baseDir + file.getName());
      zipOut.putNextEntry(entry);
      int count;
      int buffer = 1024 * 8;
      byte[] data = new byte[buffer];
      while ((count = bis.read(data, 0, buffer)) != -1) {
        zipOut.write(data, 0, count);
      }
    }
  }

  /**
   * 获取文件指定文件的指定单位的大小
   *
   * @param filePath
   * @param sizeType
   * @return
   */
  public static double getFileSize(String filePath, FileSizeType sizeType) {
    File file = new File(filePath);
    long blockSize = 0;
    if (file.isDirectory()) {
      blockSize = getFileSizes(file);
    } else {
      blockSize = file.length();
    }
    return convertFileSize(blockSize, sizeType);
  }

  /**
   * 获取指定文件夹
   *
   * @param f
   * @return
   */
  private static long getFileSizes(File f) {
    long size = 0;
    File[] flist = f.listFiles();
    assert flist != null;
    for (File file : flist) {
      if (file.isDirectory()) {
        size = size + getFileSizes(file);
      } else {
        size = size + file.length();
      }
    }
    return size;
  }

  /**
   * 转换文件大小
   *
   * @param fileSize
   * @return
   */
  public static String formatFileSize(long fileSize) {
    if (fileSize <= 0) {
      return "0B";
    }

    long b = 1024;
    long kb = b * 1024;
    long mb = kb * 1024;
    long gb = mb * 1024;
    DecimalFormat df = new DecimalFormat("#.00");
    if (fileSize < b) {
      return df.format(fileSize) + "B";
    } else if (fileSize < kb) {
      return df.format((double) fileSize / b) + "KB";
    } else if (fileSize < mb) {
      return df.format((double) fileSize / kb) + "MB";
    }
    return df.format((double) fileSize / mb) + "GB";
  }


  /**
   * 转换文件大小，指定转换的类型，保留两位小数
   *
   * @param fileSize
   * @param sizeType
   * @return
   */
  private static double convertFileSize(long fileSize, FileSizeType sizeType) {
    double fs;
    switch (sizeType) {
      case KB:
        fs = (double) fileSize / 1024;
        break;
      case MB:
        fs = (double) fileSize / 1024 / 1024;
        break;
      case GB:
        fs = (double) fileSize / 1024 / 1024 / 1024;
        break;
      default:
        fs = fileSize;
        break;
    }
    return (double) Math.round(fs * 100) / 100;
  }
}
