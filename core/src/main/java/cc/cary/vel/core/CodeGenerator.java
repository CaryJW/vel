package cc.cary.vel.core;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author Cary
 * @date 2021/05/22
 */
public class CodeGenerator {
  /**
   * <p>
   * 读取控制台内容
   * </p>
   */
  public static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("请输入" + tip + "：");
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotBlank(ipt)) {
        return ipt;
      }
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }

  /**
   * 全局配置
   *
   * @return GlobalConfig
   */
  private static GlobalConfig globalConfig() {
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/code");
    gc.setAuthor("cary");
    gc.setOpen(false);
    gc.setFileOverride(true);
    gc.setIdType(IdType.AUTO);
    gc.setActiveRecord(true);
    // XML 二级缓存
    gc.setEnableCache(false);
    // XML ResultMap
    gc.setBaseResultMap(true);
    // XML columList
    gc.setBaseColumnList(false);
    return gc;
  }

  /**
   * 数据源配置
   *
   * @return DataSourceConfig
   */
  private static DataSourceConfig dataSourceConfig() {
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDbType(DbType.MYSQL);
    dsc.setUrl("jdbc:mysql://rm-wz91h3h6e9jy449ov2o.mysql.rds.aliyuncs.com:3306/vel?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useSSL=false&serverTimezone=UTC&characterEncoding=utf-8&serverTimezone=GMT%2b8");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("jw");
    dsc.setPassword("ZZ5@!C!qMarBFbQ");
    dsc.setTypeConvert(new MySqlTypeConvert() {
      final String CHAR = "char";
      final String TEXT = "text";
      final String BIGINT = "bigint";
      final String INT = "int";
      final String DATE = "date";
      final String TIME = "time";
      final String YEAR = "year";
      final String BIT = "bit";
      final String DECIMAL = "decimal";
      final String CLOB = "clob";
      final String BLOB = "blob";
      final String BINARY = "binary";
      final String FLOAT = "float";
      final String DOUBLE = "double";
      final String JSON = "json";
      final String ENUM = "enum";

      // 自定义数据库表字段类型转换【可选】
      @Override
      public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        if (fieldType.contains(CHAR) || fieldType.contains(TEXT)) {
          return DbColumnType.STRING;
        } else if (fieldType.contains(BIGINT)) {
          return DbColumnType.LONG;
        } else if (fieldType.contains(INT)) {
          return DbColumnType.INTEGER;
        } else if (fieldType.contains(DATE) || fieldType.contains(TIME) || fieldType.contains(YEAR)) {
          return DbColumnType.STRING;
        } else if (fieldType.contains(BIT)) {
          return DbColumnType.BOOLEAN;
        } else if (fieldType.contains(DECIMAL)) {
          return DbColumnType.BIG_DECIMAL;
        } else if (fieldType.contains(CLOB)) {
          return DbColumnType.CLOB;
        } else if (fieldType.contains(BLOB)) {
          return DbColumnType.BLOB;
        } else if (fieldType.contains(BINARY)) {
          return DbColumnType.BYTE_ARRAY;
        } else if (fieldType.contains(FLOAT)) {
          return DbColumnType.FLOAT;
        } else if (fieldType.contains(DOUBLE)) {
          return DbColumnType.DOUBLE;
        } else if (fieldType.contains(JSON) || fieldType.contains(ENUM)) {
          return DbColumnType.STRING;
        }
        return super.processTypeConvert(globalConfig, fieldType);
      }
    });
    return dsc;
  }

  /**
   * 包配置
   *
   * @return
   */
  private static PackageConfig packageConfig() {
    PackageConfig pc = new PackageConfig();
    pc.setModuleName(scanner("模块名"));
    pc.setParent("cc.cary.vel.core");
    return pc;
  }

  /**
   * 策略配置
   *
   * @return
   */
  private static StrategyConfig strategyConfig() {
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true);
    strategy.setRestControllerStyle(true);
    strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
    // 去掉表中前缀
    strategy.setTablePrefix("v_", "t_");
    return strategy;
  }

  public static void main(String[] args) {
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();
    mpg.setGlobalConfig(globalConfig());
    mpg.setDataSource(dataSourceConfig());
    mpg.setPackageInfo(packageConfig());
    mpg.setStrategy(strategyConfig());
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }
}
