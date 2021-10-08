package cc.cary.vel.core.common.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RenderUtils
 *
 * @author Cary
 * @date 2021/05/22
 */
public class RenderUtils {
  public static void renderJson(HttpServletResponse response, Object object) throws IOException {
    response.setContentType("application/json;charset=utf-8");

    response.getWriter().write(JSON.toJSONString(object));
    response.getWriter().close();
  }
}
