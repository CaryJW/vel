package cc.cary.vel.adminapi.controllers.business;

import cc.cary.vel.core.common.annotation.Limit;
import cc.cary.vel.core.common.entities.UploadResult;
import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.common.upload.UploadUtil;
import cc.cary.vel.core.common.utils.VelDateUtils;
import cc.cary.vel.core.common.utils.VelFileUtils;
import cc.cary.vel.core.sys.services.IAdminUserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * TestController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/test")
@Validated
@Slf4j
public class TestController {

  @Autowired
  private IAdminUserService userService;
  @Autowired
  private UploadUtil uploadUtil;

  @RequestMapping("/test")
  public ResultData test() {
    return ResultData.ok().put("list", userService.list());
  }

  @RequestMapping("/list")
  public ResultData list() {
    return ResultData.ok().put("list", userService.list());
  }

  @RequestMapping("/limit")
  @Limit(name = "测试", key = "test", prefix = "limit", count = 3, period = 10)
  public ResultData limit() {
    return ResultData.ok();
  }

  @RequestMapping("/secretKey")
  public ResultData secretKey() {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    String secretString = Encoders.BASE64.encode(key.getEncoded());
    return ResultData.ok().put("secretkey", secretString);
  }

  @RequestMapping("/token")
  public ResultData token() {
    String token = Jwts.builder()
        .setSubject("123")
        .setIssuedAt(new Date())
        .setIssuer("cary")
        .signWith(generalKey())
        .compact();
    return ResultData.ok().put("token", token);
  }

  @RequestMapping("/claimByToken")
  public ResultData claimByToken(@RequestParam String token) {
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(generalKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
      return ResultData.ok().put("res", claims.getSubject());
    } catch (JwtException e) {
      throw new AppException("解密失败");
    }
  }

  private SecretKey generalKey() {
    String secert = "6sqGjmX3m6R4TMAVIQ+GmNdJJuGxzCp3FMeOn3WSVF0=";
    return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secert));
  }

  @RequestMapping("/upload")
  public ResultData upload(@Valid MultipartFile file) {
    UploadResult uploadResult;
    try {
      uploadResult = uploadUtil.upload(file);
    } catch (Exception e) {
      return ResultData.fail("文件上传失败");
    }
    return ResultData.ok().put("uploadResult", uploadResult);
  }

  @RequestMapping("/file/delete")
  public ResultData delete(@RequestParam String url) {
    uploadUtil.delete(url);
    return ResultData.ok();
  }

  @RequestMapping("/xss")
  public ResultData xss(@RequestParam List<String> abc) {
    return ResultData.ok().put("abc", ArrayUtils.toString(abc, ","));
  }

  private List<DemoData> data() {
    List<DemoData> list = new ArrayList<DemoData>();
    final int count = 10;
    for (int i = 0; i < count; i++) {
      DemoData data = new DemoData();
      data.setString("字符串" + i);
      data.setDate(new Date());
      data.setDoubleData(0.56);
      list.add(data);
    }
    return list;
  }

  @RequestMapping("/export")
  public ResultData export() throws IOException {
    File file = File.createTempFile("测试啊", ".xlsx");
    EasyExcel.write(file, DemoData.class).sheet("模板").doWrite(data());
    return ResultData.ok().put("size", VelFileUtils.formatFileSize(file.length()));
  }

  @RequestMapping("/exception")
  public ResultData exception() {
    try {
      int i = 3 / 0;
    } catch (Exception e) {
      log.error("发送错误哦：{}", e.getMessage());
      e.printStackTrace();
    }
    return ResultData.ok();
  }

  @GetMapping("/generateRangeEveryDay")
  public ResultData generateRangeEveryDay() {
    List<String> days = VelDateUtils.generateRangeEveryDay("2021-07-12", "2021-08-10");
    return ResultData.ok().put("days", days);
  }

  @Data
  static class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
  }
}
