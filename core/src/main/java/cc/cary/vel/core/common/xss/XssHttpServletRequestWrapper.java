package cc.cary.vel.core.common.xss;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.stream.IntStream;

/**
 * Jsoup过滤 http请求，防止 Xss攻击
 *
 * @author Cary
 * @date 2021/05/22
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
  private final HttpServletRequest orgRequest;
  private final boolean isIncludeRichText;

  public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
    super(request);
    this.orgRequest = request;
    this.isIncludeRichText = isIncludeRichText;
  }

  /**
   * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
   * <p>
   * 覆盖 getParameter方法，将参数名和参数值都做xss过滤
   * 如果需要获得原始的值，则通过 super.getParameterValues(name)来获取
   * getParameterNames,getParameterValues和 getParameterMap也可能需要覆盖
   */
  @Override
  public String getParameter(String name) {
    if (!isCleanRichText(name)) {
      return super.getParameter(name);
    }
    name = JsoupUtil.clean(name);
    String value = super.getParameter(name);
    if (StringUtils.isNotBlank(value)) {
      value = JsoupUtil.clean(value);
    }
    return value;
  }

  /**
   * 获取指定参数名的所有值的数组，如：checkbox的所有数据
   *
   * @param name
   * @return
   */
  @Override
  public String[] getParameterValues(String name) {
    if (!isCleanRichText(name)) {
      return super.getParameterValues(name);
    }

    String[] arr = super.getParameterValues(name);
    if (arr != null) {
      IntStream.range(0, arr.length).forEach(i -> arr[i] = JsoupUtil.clean(arr[i]));
    }
    return arr;
  }

  /**
   * 覆盖getHeader方法，将参数名和参数值都做 xss过滤
   * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
   * getHeaderNames 也可能需要覆盖
   */
  @Override
  public String getHeader(String name) {
    name = JsoupUtil.clean(name);
    String value = super.getHeader(name);
    if (StringUtils.isNotBlank(value)) {
      value = JsoupUtil.clean(value);
    }
    return value;
  }

  /**
   * 过滤富文本
   *
   * @param name
   * @return true 过滤
   */
  private boolean isCleanRichText(String name) {
    return ("content".equals(name) || name.endsWith("WithHtml")) && isIncludeRichText;
  }

  /**
   * 获取原始的 request
   */
  private HttpServletRequest getOrgRequest() {
    return orgRequest;
  }

  /**
   * 获取原始的 request的静态方法
   */
  public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
    if (req instanceof XssHttpServletRequestWrapper) {
      return ((XssHttpServletRequestWrapper) req).getOrgRequest();
    }
    return req;
  }

}
