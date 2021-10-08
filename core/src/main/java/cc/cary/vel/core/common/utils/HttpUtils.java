package cc.cary.vel.core.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * HttpUtils
 *
 * @author Cary
 * @date 2021/05/22
 */
public class HttpUtils {

  /**
   * get
   *
   * @param host
   * @return
   * @throws Exception
   */
  public static HttpResponse doGet(String host)
      throws Exception {
    return doGet(host, null);
  }

  /**
   * get
   *
   * @param host
   * @param querys
   * @return
   * @throws Exception
   */
  public static HttpResponse doGet(String host, Map<String, String> querys)
      throws Exception {
    return doGet(host, "", querys);
  }

  /**
   * get
   *
   * @param host
   * @param path
   * @param querys
   * @return
   * @throws Exception
   */
  public static HttpResponse doGet(String host, String path, Map<String, String> querys)
      throws Exception {
    return doGet(host, path, null, querys);
  }

  /**
   * get
   *
   * @param host
   * @param path
   * @param headers
   * @param querys
   * @return
   * @throws Exception
   */
  public static HttpResponse doGet(String host, String path,
                                   Map<String, String> headers,
                                   Map<String, String> querys)
      throws Exception {
    HttpClient httpClient = wrapClient(host);
    HttpGet request = new HttpGet(buildUrl(host, path, querys));
    if (headers != null) {
      for (Map.Entry<String, String> e : headers.entrySet()) {
        request.addHeader(e.getKey(), e.getValue());
      }
    }
    return httpClient.execute(request);
  }

  /**
   * post form
   *
   * @param host
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host) throws Exception {
    return doPost(host, null);
  }

  /**
   * post form
   *
   * @param host
   * @param bodys
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host, Map<String, String> bodys) throws Exception {
    return doPost(host, "", bodys);
  }

  /**
   * post form
   *
   * @param host
   * @param path
   * @param bodys
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host, String path, Map<String, String> bodys) throws Exception {
    return doPost(host, path, null, bodys);
  }

  /**
   * post form
   *
   * @param host
   * @param path
   * @param headers
   * @param bodys
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host, String path,
                                    Map<String, String> headers,
                                    Map<String, String> bodys)
      throws Exception {
    HttpClient httpClient = wrapClient(host);
    HttpPost request = buildPost(host, path, headers);

    if (bodys != null) {
      List<NameValuePair> nameValuePairList = new ArrayList<>();

      for (String key : bodys.keySet()) {
        nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
      }
      UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, StandardCharsets.UTF_8);
      formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
      request.setEntity(formEntity);
    }
    return httpClient.execute(request);
  }

  /**
   * Post String
   *
   * @param host
   * @param path
   * @param headers
   * @param body
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host, String path,
                                    Map<String, String> headers,
                                    String body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);
    HttpPost request = buildPost(host, path, headers);

    if (StringUtils.isNotBlank(body)) {
      request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
    }
    return httpClient.execute(request);
  }

  /**
   * Post stream
   *
   * @param host
   * @param path
   * @param headers
   * @param body
   * @return
   * @throws Exception
   */
  public static HttpResponse doPost(String host, String path,
                                    Map<String, String> headers,
                                    byte[] body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);
    HttpPost request = buildPost(host, path, headers);

    if (body != null) {
      request.setEntity(new ByteArrayEntity(body));
    }
    return httpClient.execute(request);
  }

  /**
   * Put String
   *
   * @param host
   * @param path
   * @param headers
   * @param querys
   * @param body
   * @return
   * @throws Exception
   */
  public static HttpResponse doPut(String host, String path,
                                   Map<String, String> headers,
                                   Map<String, String> querys,
                                   String body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPut request = new HttpPut(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (StringUtils.isNotBlank(body)) {
      request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
    }

    return httpClient.execute(request);
  }

  /**
   * Put stream
   *
   * @param host
   * @param path
   * @param headers
   * @param querys
   * @param body
   * @return
   * @throws Exception
   */
  public static HttpResponse doPut(String host, String path,
                                   Map<String, String> headers,
                                   Map<String, String> querys,
                                   byte[] body)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpPut request = new HttpPut(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    if (body != null) {
      request.setEntity(new ByteArrayEntity(body));
    }

    return httpClient.execute(request);
  }

  /**
   * Delete
   *
   * @param host
   * @param path
   * @param headers
   * @param querys
   * @return
   * @throws Exception
   */
  public static HttpResponse doDelete(String host, String path,
                                      Map<String, String> headers,
                                      Map<String, String> querys)
      throws Exception {
    HttpClient httpClient = wrapClient(host);

    HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
    for (Map.Entry<String, String> e : headers.entrySet()) {
      request.addHeader(e.getKey(), e.getValue());
    }

    return httpClient.execute(request);
  }

  /**
   * parseResponseToString
   *
   * @param response
   * @return
   * @throws IOException
   */
  public static String parseResponseToString(HttpResponse response) throws IOException {
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      return EntityUtils.toString(response.getEntity());
    } else {
      throw new IOException("HttpUtils status error, code: " + response.getStatusLine().getStatusCode());
    }
  }

  /**
   * parseResponseToJson
   *
   * @param response
   * @return
   * @throws IOException
   */
  public static JSONObject parseResponseToJson(HttpResponse response) throws IOException {
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      return JSON.parseObject(EntityUtils.toString(response.getEntity()));
    } else {
      throw new IOException("HttpUtils status error, code: " + response.getStatusLine().getStatusCode());
    }
  }

  private static HttpPost buildPost(String host, String path,
                                    Map<String, String> headers) throws UnsupportedEncodingException {
    HttpPost request = new HttpPost(buildUrl(host, path, null));
    if (headers != null) {
      for (Map.Entry<String, String> e : headers.entrySet()) {
        request.addHeader(e.getKey(), e.getValue());
      }
    }
    return request;
  }

  private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    StringBuilder sbUrl = new StringBuilder();
    sbUrl.append(host);
    if (!StringUtils.isBlank(path)) {
      sbUrl.append(path);
    }
    if (null != querys) {
      StringBuilder sbQuery = new StringBuilder();
      for (Map.Entry<String, String> query : querys.entrySet()) {
        if (0 < sbQuery.length()) {
          sbQuery.append("&");
        }
        if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
          sbQuery.append(query.getValue());
        }
        if (!StringUtils.isBlank(query.getKey())) {
          sbQuery.append(query.getKey());
          if (!StringUtils.isBlank(query.getValue())) {
            sbQuery.append("=");
            sbQuery.append(URLEncoder.encode(query.getValue(), StandardCharsets.UTF_8.name()));
          }
        }
      }
      if (0 < sbQuery.length()) {
        sbUrl.append("?").append(sbQuery);
      }
    }

    return sbUrl.toString();
  }

  private static HttpClient wrapClient(String host) {
    HttpClient httpClient = HttpClientBuilder.create().build();
    String hostType = "https://";
    if (host.startsWith(hostType)) {
      return sslClient();
    }

    return httpClient;
  }

  /**
   * 配置SSL绕过https证书
   *
   * @return
   */
  private static HttpClient sslClient() {
    try {
      SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
      // 在调用SSL之前需要重写验证方法，取消检测SSL
      X509TrustManager tm = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] xcs, String str) {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] xcs, String str) {

        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
      // 创建Registry
      RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
          .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
          .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
      Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
          .register("http", PlainConnectionSocketFactory.INSTANCE)
          .register("https", socketFactory).build();
      // 创建ConnectionManager，添加Connection配置信息
      PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

      return HttpClients.custom().setConnectionManager(connectionManager)
          .setDefaultRequestConfig(requestConfig).build();
    } catch (KeyManagementException | NoSuchAlgorithmException ex) {
      throw new RuntimeException(ex);
    }
  }
}
