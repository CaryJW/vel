package cc.cary.vel.core.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 根据IP获取地址信息工具
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
public class AddressUtils {

  public static String getCityInfo(String ip) {
    DbSearcher searcher = null;
    try {
      String dbPath = AddressUtils.class.getResource("/ip2region/ip2region.db").getPath();
      File file = new File(dbPath);
      if (!file.exists()) {
        String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
        dbPath = tmpDir + "ip.db";
        file = new File(dbPath);
        FileUtils.copyInputStreamToFile(Objects.requireNonNull(AddressUtils.class.getClassLoader().getResourceAsStream("classpath:ip2region/ip2region.db")), file);
      }
      DbConfig config = new DbConfig();
      searcher = new DbSearcher(config, file.getPath());
      Method method = searcher.getClass().getMethod("btreeSearch", String.class);
      if (!Util.isIpAddress(ip)) {
        log.error("Error: Invalid ip address");
      }
      DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
      return dataBlock.getRegion();
    } catch (Exception e) {
      log.error("获取地址信息异常：{}", e.getMessage());
    } finally {
      if (searcher != null) {
        try {
          searcher.close();
        } catch (IOException e) {
          log.error("DbSearcher实例关闭异常：{}", e.getMessage());
        }
      }
    }
    return "";
  }

}