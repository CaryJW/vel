package cc.cary.vel.serviceapi.controlles;

import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.common.signature.ISignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 *
 * @author Cary
 * @date 2021/8/20
 */
@RestController
@RequestMapping("/test")
public class TestController {
  @Autowired
  private ISignatureService signatureService;

  private final String KEY = "dsf6454sdf15fd";
  private final String SECRET = "464sdf84651f6s54df68sfdsfqej";

  @GetMapping("/sign")
  private ResultData sign() {
    Map<String, Object> params = new HashMap<>(16);
    params.put("id", 1);
    params.put("name", "cary");
    signatureService.sign(params, KEY, SECRET);
    return ResultData.ok().put("res", params);
  }

  @GetMapping("/verify")
  private ResultData verify(HttpServletRequest request) {
    return ResultData.ok().put("res", signatureService.verify(request, SECRET));
  }
}
