package cc.cary.vel.core.common.configure;

import cc.cary.vel.core.common.utils.VelDateUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * 自定义 p6spy sql输出格式
 *
 * @author Cary
 * @date 2021/05/22
 */
public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
    return StringUtils.isNotBlank(sql) ? VelDateUtils.formatFullTime(LocalDateTime.now(), VelDateUtils.FULL_TIME_SPLIT_PATTERN)
        + " | 耗时 " + elapsed + " ms | SQL 语句：" + StringUtils.LF + sql.replaceAll("[\\s]+", StringUtils.SPACE) + ";" : StringUtils.EMPTY;
  }
}
