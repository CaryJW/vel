package cc.cary.vel.core.sys.vo;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author Cary
 * @date 2021/9/27
 */
@Data
public class PasswordStrategyVo {
  private int failLoginCount;
  private int failLoginTimeType;
  private long failLoginTime;
  private int unlockTimeType;
  private long unlockTime;
  private int passwordMixLength;
  List<Integer> passwordComplexity;
}
