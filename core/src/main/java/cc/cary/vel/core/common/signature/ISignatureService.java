package cc.cary.vel.core.common.signature;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 签名服务
 *
 * @author Cary
 * @date 2021/8/20
 */
public interface ISignatureService {

  /**
   * 对请求参数进行签名
   *
   * @param params 参数
   * @param key    key
   * @param secret secret
   */
  void sign(Map<String, Object> params, String key, String secret);

  /**
   * 验证签名
   *
   * @param request 请求
   * @param secret  secret
   * @return
   */
  boolean verify(HttpServletRequest request, String secret);
}
