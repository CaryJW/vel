package cc.cary.vel.core.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * VelUtils
 *
 * @author Cary
 * @date 2021/05/22
 */
@Slf4j
public class VelUtils {
  /**
   * 驼峰转下划线
   *
   * @param value 待转换值
   * @return 结果
   */
  public static String camelToUnderscore(String value) {
    if (StringUtils.isBlank(value)) {
      return value;
    }
    String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
    if (arr.length == 0) {
      return value;
    }
    StringBuilder result = new StringBuilder();
    IntStream.range(0, arr.length).forEach(i -> {
      if (i != arr.length - 1) {
        result.append(arr[i]).append(StringPool.UNDERSCORE);
      } else {
        result.append(arr[i]);
      }
    });
    return StringUtils.lowerCase(result.toString());
  }

  /**
   * 下划线转驼峰
   *
   * @param value 待转换值
   * @return 结果
   */
  public static String underscoreToCamel(String value) {
    StringBuilder result = new StringBuilder();
    String[] arr = value.split("_");
    for (String s : arr) {
      result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
    }
    return result.toString();
  }

  /**
   * 获取客户端信息
   *
   * @return
   */
  public static Map<String, String> getSystemBrowserInfo() {
    Map<String, String> info = new HashMap<>(16);
    try {
      HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

      StringBuilder userAgent = new StringBuilder("[");
      userAgent.append(request.getHeader("User-Agent"));
      userAgent.append("]");

      info.put("system", getSystem(userAgent));
      info.put("browser", getBrowser(userAgent));
    } catch (Exception e) {
      log.error("获取登录信息失败：{}", e.getMessage());
      info.put("system", "");
      info.put("browser", "");
    }
    return info;
  }

  private static String getSystem(StringBuilder userAgent) {
    int indexOfMac = userAgent.indexOf("Mac OS X");
    int indexOfWindows = userAgent.indexOf("Windows NT");
    boolean isMac = indexOfMac > 0;
    boolean isLinux = userAgent.indexOf("Linux") > 0;
    boolean isWindows = indexOfWindows > 0;

    String os = "";
    if (isMac) {
      os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
    } else if (isLinux) {
      os = "Linux";
    } else if (isWindows) {
      os = "Windows ";
      String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
          + "Windows NTx.x".length());
      switch (version.trim()) {
        case "5.0":
          os += "2000";
          break;
        case "5.1":
          os += "XP";
          break;
        case "5.2":
          os += "2003";
          break;
        case "6.0":
          os += "Vista";
          break;
        case "6.1":
          os += "7";
          break;
        case "6.2":
          os += "8";
          break;
        case "6.3":
          os += "8.1";
          break;
        case "10":
          os += "10";
          break;
        default:
      }
    }
    return os;
  }

  private static String getBrowser(StringBuilder userAgent) {
    int indexOfIe = userAgent.indexOf("MSIE");
    int indexOfIe11 = userAgent.indexOf("rv:");
    int indexOfFf = userAgent.indexOf("Firefox");
    int indexOfSogou = userAgent.indexOf("MetaSr");
    int indexOfChrome = userAgent.indexOf("Chrome");
    int indexOfSafari = userAgent.indexOf("Safari");
    int indexOfWindows = userAgent.indexOf("Windows NT");

    boolean isWindows = indexOfWindows > 0;
    boolean containIe = indexOfIe > 0 || (isWindows && (indexOfIe11 > 0));
    boolean containFf = indexOfFf > 0;
    boolean containSogou = indexOfSogou > 0;
    boolean containChrome = indexOfChrome > 0;
    boolean containSafari = indexOfSafari > 0;

    String browser = "";
    if (containSogou) {
      if (containIe) {
        browser = "搜狗" + userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
      } else if (containChrome) {
        browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
      }
    } else if (containChrome) {
      browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
    } else if (containSafari) {
      int indexOfSafariVersion = userAgent.indexOf("Version");
      browser = "Safari "
          + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
    } else if (containFf) {
      browser = userAgent.substring(indexOfFf, indexOfFf + "Firefox/xx".length());
    } else if (containIe) {
      if (indexOfIe11 > 0) {
        browser = "IE 11";
      } else {
        browser = userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
      }
    }
    return StringUtils.replace(browser, "/", " ");
  }

  /**
   * 判断是否为 ajax请求
   *
   * @param request HttpServletRequest
   * @return boolean
   */
  public static boolean isAjaxRequest(HttpServletRequest request) {
    return (request.getHeader("X-Requested-With") != null
        && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
  }

  /**
   * 判断是否网络请求
   *
   * @param urls
   * @return
   */
  public static boolean isHttpUrl(String urls) {
    String http = "http://";
    String https = "https://";
    return urls.startsWith(http) || urls.startsWith(https);
  }

  /**
   * 正则校验
   *
   * @param regex 正则表达式字符串
   * @param value 要匹配的字符串
   * @return 正则校验结果
   */
  public static boolean match(String regex, String value) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
