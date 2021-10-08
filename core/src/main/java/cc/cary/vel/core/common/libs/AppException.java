package cc.cary.vel.core.common.libs;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author Cary
 * @date 2021/05/22
 */
@Data
public class AppException extends RuntimeException {
  private int code = 500;

  public AppException(int code) {
    this(code, "");
  }

  public AppException(String msg) {
    super(msg);
  }

  public AppException(int code, String msg) {
    super(msg);
    this.code = code;
  }


  public AppException(ResultData result) {
    this(result.getCode(), result.getMessage());
  }
}
