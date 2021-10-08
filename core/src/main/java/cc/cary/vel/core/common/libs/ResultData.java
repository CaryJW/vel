package cc.cary.vel.core.common.libs;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * 相应实体
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class ResultData {
  static private String DEFAULT_MESSAGE_SUCCESS = "success";
  static private String DEFAULT_MESSAGE_FAILED = "failed";

  private int code = 0;
  private String message;
  private Map<String, Object> data = new HashMap<>();

  private ResultData() {
  }

  public static ResultData ok() {
    ResultData resultData = new ResultData();
    resultData.setCode(ResultCode.SUCCESS);
    resultData.setMessage(DEFAULT_MESSAGE_SUCCESS);
    return resultData;
  }

  public static ResultData fail(int code, String message) {
    ResultData resultData = new ResultData();
    resultData.setMessage(message);
    resultData.setCode(code);
    return resultData;
  }

  public static ResultData fail(int code) {
    return fail(code, DEFAULT_MESSAGE_FAILED);
  }

  public static ResultData fail(String message) {
    return fail(ResultCode.ERROR, message);
  }

  public static ResultData fail() {
    return fail(ResultCode.ERROR, DEFAULT_MESSAGE_FAILED);
  }

  public ResultData put(String key, Object value) {
    data.put(key, value);
    return this;
  }

  public <T> ResultData put(IPage<T> page) {
    this.put("list", page.getRecords())
        .put("total", page.getTotal());
    return this;
  }

  public ResultData put(BindingResult result) {
    Map<String, Object> errorMap = new HashMap<>(16);
    for (FieldError err : result.getFieldErrors()) {
      errorMap.put(err.getField(), err.getDefaultMessage());
    }
    this.code = ResultCode.ERROR;
    this.message = "invalid params.";
    this.data = errorMap;
    return this;
  }
}
