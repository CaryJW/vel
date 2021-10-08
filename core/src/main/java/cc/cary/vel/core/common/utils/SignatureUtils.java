package cc.cary.vel.core.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 签名工具
 *
 * @author Cary
 * @date 2021/8/20
 */
public class SignatureUtils {
  /**
   * 参数排序后期进行签名
   *
   * @param params     参数
   * @param secretName secret字段名
   * @param secret     secret值
   * @return
   */
  public static String sortSign(Map<String, ?> params, String secretName, String secret) {
    List<String> keys = new ArrayList<>(params.keySet());
    Collections.sort(keys);

    StringBuilder signStr = new StringBuilder();
    for (String key : keys) {
      String value = String.valueOf(params.get(key));
      if (Strings.isNotBlank(value)) {
        signStr
            .append(key)
            .append("=")
            .append(value)
            .append("&");
      }
    }
    signStr.append(secretName).append("=").append(secret);
    return DigestUtils.md5Hex(signStr.toString()).toLowerCase();
  }
}
