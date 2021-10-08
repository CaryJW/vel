package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.sys.entities.DictData;
import cc.cary.vel.core.sys.vo.PasswordStrategyVo;

import java.util.List;

/**
 * IConfigureService
 *
 * @author Cary
 * @date 2021/9/27
 */
public interface IConfigureService {
  /**
   * 获取配置
   *
   * @param value
   * @return
   */
  List<DictData> getConfigure();

  /**
   * 根据值获取配置
   *
   * @param value
   * @return
   */
  PasswordStrategyVo getConfigureByValue(String value);

  /**
   * 保存密码策略
   *
   * @param passwordStrategy
   */
  void savePasswordStrategy(PasswordStrategyVo passwordStrategy);
}
