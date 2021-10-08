package cc.cary.vel.core.common.utils;

import cc.cary.vel.core.common.libs.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 排序工具
 *
 * @author Cary
 * @date 2021/05/22
 */
public class QueryUtils {
  private static final String PLUS = "+";

  public static void parseParamsDate(Map<String, Object> params, String star, String end) {
    if (params.get(star) != null && StringUtils.isNotEmpty(params.get(star) + "")) {
      params.put(star, params.get(star) + " 00:00:00");
    }
    if (params.get(end) != null && StringUtils.isNotEmpty(params.get(star) + "")) {
      params.put(end, params.get(end) + " 23:59:59");
    }
  }

  public static <T> Page<T> buildPage(QueryRequest request, boolean camelToUnderscore) {
    Page<T> page = new Page<T>();
    page.setCurrent(request.getPage());
    page.setSize(request.getLimit());

    String[] sorts = request.getSort().split(",");
    List<OrderItem> items = new ArrayList<>();
    for (String ss : sorts) {
      String sort = ss.substring(0, 1);
      String field = ss.substring(1);
      if (camelToUnderscore) {
        field = VelUtils.camelToUnderscore(field);
      }
      if (PLUS.equals(sort)) {
        items.add(OrderItem.asc(field));
      } else {
        items.add(OrderItem.desc(field));
      }
    }
    page.setOrders(items);
    return page;
  }
}
