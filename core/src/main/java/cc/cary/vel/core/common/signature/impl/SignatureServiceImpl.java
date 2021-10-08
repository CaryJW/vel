package cc.cary.vel.core.common.signature.impl;

import cc.cary.vel.core.common.properties.SignProperties;
import cc.cary.vel.core.common.signature.ISignatureService;
import cc.cary.vel.core.common.utils.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名服务
 *
 * @author Cary
 * @date 2021/8/20
 */
@Service
public class SignatureServiceImpl implements ISignatureService {
  @Autowired
  private SignProperties signProperties;

  @Override
  public void sign(Map<String, Object> params, String key, String secret) {
    params.put(signProperties.getFieldName().getTime(), System.currentTimeMillis() / 1000);
    params.put(signProperties.getFieldName().getKey(), key);
    String sign = SignatureUtils.sortSign(params, secret, signProperties.getFieldName().getSecret());
    params.put(signProperties.getFieldName().getSign(), sign);
  }

  @Override
  public boolean verify(HttpServletRequest request, String secret) {
    if (StringUtils.isBlank(request.getParameter(signProperties.getFieldName().getSign())) ||
        StringUtils.isBlank(request.getParameter(signProperties.getFieldName().getTime())) ||
        StringUtils.isBlank(request.getParameter(signProperties.getFieldName().getKey()))
    ) {
      return false;
    }
    Map<String, String> params = getParams(request);
    String remoteSign = params.get(signProperties.getFieldName().getSign());
    params.remove(signProperties.getFieldName().getSign());
    if (!SignatureUtils.sortSign(params, secret, signProperties.getFieldName().getSecret()).equalsIgnoreCase(remoteSign)) {
      return false;
    }
    return true;
  }

  private TreeMap<String, String> getParams(HttpServletRequest request) {
    Map<String, String[]> p = request.getParameterMap();
    TreeMap<String, String> result = new TreeMap<>();
    for (String key : p.keySet()) {
      result.put(key, p.get(key)[0]);
    }
    return result;
  }
}
