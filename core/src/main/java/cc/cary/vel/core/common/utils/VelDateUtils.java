package cc.cary.vel.core.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间转换工具
 *
 * @author Cary
 * @date 2021/05/22
 */
public class VelDateUtils {

  /**
   * yyyy
   */
  public static final String YEAR_PATTERN = "yyyy";

  /**
   * yyyyMMdd
   */
  public static final String FULL_DATE_PATTERN = "yyyyMMdd";

  /**
   * yyyy-MM
   */
  public static final String MONTH_TIME_SPLIT_PATTERN = "yyyy-MM";

  /**
   * yyyy-MM-dd
   */
  public static final String DATE_TIME_SPLIT_PATTERN = MONTH_TIME_SPLIT_PATTERN + "-dd";

  /**
   * yyyy-MM-dd HH:mm:ss
   */
  public static final String FULL_TIME_SPLIT_PATTERN = DATE_TIME_SPLIT_PATTERN + " HH:mm:ss";

  public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

  /**
   * 字符串 转 LocalDate，格式：yyyy-MM-dd
   *
   * @param dateStr
   * @return
   */
  public static LocalDate parseToLocalDate(String dateStr) {
    return parseToLocalDate(dateStr, DATE_TIME_SPLIT_PATTERN);
  }

  /**
   * 字符串 转 LocalDateTime，yyyy-MM-dd HH:mm:ss
   *
   * @param dateStr
   * @return
   */
  public static LocalDateTime parseToLocalDateTime(String dateStr) {
    return parseToLocalDateTime(dateStr, FULL_TIME_SPLIT_PATTERN);
  }

  /**
   * 指定格式字符串 转 LocalDate
   *
   * @param dateStr
   * @param pattern
   * @return
   */
  public static LocalDate parseToLocalDate(String dateStr, String pattern) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
    return LocalDate.parse(dateStr, df);
  }

  /**
   * 指定格式字符串 转 LocalDateTime
   *
   * @param dateStr
   * @param pattern
   * @return
   */
  public static LocalDateTime parseToLocalDateTime(String dateStr, String pattern) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
    return LocalDateTime.parse(dateStr, df);
  }

  /**
   * LocalDate 转 字符串，格式：yyyy-MM-dd HH:mm:ss
   *
   * @param localDate
   * @return
   */
  public static String formatFullTime(LocalDate localDate) {
    return formatFullTime(localDate, FULL_TIME_SPLIT_PATTERN);
  }

  /**
   * 指定格式LocalDate 转 字符串
   *
   * @param localDate
   * @param pattern
   * @return
   */
  public static String formatFullTime(LocalDate localDate, String pattern) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    return localDate.format(dateTimeFormatter);
  }

  /**
   * LocalDateTime 转 字符串，格式：yyyy-MM-dd HH:mm:ss
   *
   * @param localDateTime
   * @return
   */
  public static String formatFullTime(LocalDateTime localDateTime) {
    return formatFullTime(localDateTime, FULL_TIME_SPLIT_PATTERN);
  }

  /**
   * 指定格式LocalDateTime 转 字符串
   *
   * @param localDateTime
   * @param pattern
   * @return
   */
  public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    return localDateTime.format(dateTimeFormatter);
  }

  /**
   * 指定格式date 转 字符串，按中国时间格式
   * SimpleDateFormat是线程不安全的
   * Locale 默认习惯格式化 CHINA 中国 US美国
   *
   * @param date
   * @param dateFormatType
   * @return
   */
  public static String getDateFormat(Date date, String dateFormatType) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
    return simpleDateFormat.format(date);
  }

  /**
   * 指定格式date 转 字符串，按美国时间格式
   *
   * @param date
   * @param format
   * @return
   * @throws ParseException
   */
  public static String formatCstTime(String date, String format) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
    Date usDate = simpleDateFormat.parse(date);
    return VelDateUtils.getDateFormat(usDate, format);
  }

  /**
   * 使用Instant和区域ID创建LocalDateTime的实例
   *
   * @param instant 传递给创建 localDateTime 的瞬间
   * @param format  创建偏移量的时区
   * @return
   */
  public static String formatInstant(Instant instant, String format) {
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    return localDateTime.format(DateTimeFormatter.ofPattern(format));
  }

  /**
   * 字符串 转 Date，格式：yyyy-MM-dd HH:mm:ss
   *
   * @param dateStr
   * @return
   */
  public static Date parseToDateTime(String dateStr) {
    if (StringUtils.isEmpty(dateStr)) {
      return null;
    }

    try {
      return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, VelDateUtils.FULL_TIME_SPLIT_PATTERN);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * 指定格斯字符串 转 Date
   *
   * @param dateStr
   * @param pattern
   * @return
   */
  public static Date parseToDateTime(String dateStr, String pattern) {
    if (StringUtils.isEmpty(dateStr)) {
      return null;
    }

    try {
      return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, pattern);
    } catch (ParseException e) {
      return null;
    }
  }

  public static final int TYPE_YEAR = 1;
  public static final int TYPE_MONTH = 2;
  public static final int TYPE_DATE = 3;

  /**
   * 生成指定日期内的每一天
   *
   * @param start
   * @param end
   * @return
   */
  public static List<String> generateRangeEveryDay(String start, String end) {
    return VelDateUtils.generateRangeEveryDay(TYPE_DATE, start, end);
  }

  /**
   * 生成指定日期内的每一天、月、年
   *
   * @param type
   * @param start
   * @param end
   * @return
   */
  public static List<String> generateRangeEveryDay(int type, String start, String end) {
    if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
      return null;
    }
    // 相差天数
    LocalDate startDate = parseToLocalDate(start);
    LocalDate endDate = parseToLocalDate(end);
    if (startDate.isAfter(endDate)) {
      return null;
    }

    List<String> days = new ArrayList<>();
    switch (type) {
      case TYPE_YEAR:
        long differYears = startDate.until(endDate, ChronoUnit.YEARS);
        for (long i = 0; i <= differYears; i++) {
          LocalDate yd = startDate.plusYears(i);
          days.add(formatFullTime(yd, YEAR_PATTERN));
        }
        return days;
      case TYPE_MONTH:
        long differMonths = startDate.until(endDate, ChronoUnit.MONTHS);
        for (long i = 0; i <= differMonths; i++) {
          LocalDate yd = startDate.plusMonths(i);
          days.add(formatFullTime(yd, MONTH_TIME_SPLIT_PATTERN));
        }
        return days;
      default:
        long differDays = startDate.until(endDate, ChronoUnit.DAYS);
        for (long i = 0; i <= differDays; i++) {
          LocalDate yd = startDate.plusDays(i);
          days.add(formatFullTime(yd, DATE_TIME_SPLIT_PATTERN));
        }
        return days;
    }
  }
}
