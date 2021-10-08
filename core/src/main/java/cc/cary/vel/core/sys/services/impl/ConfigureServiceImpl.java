package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.sys.entities.DictData;
import cc.cary.vel.core.sys.services.IConfigureService;
import cc.cary.vel.core.sys.services.IDictDataService;
import cc.cary.vel.core.sys.vo.PasswordStrategyVo;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ConfigureServiceImpl
 *
 * @author Cary
 * @date 2021/9/27
 */
@Service
public class ConfigureServiceImpl implements IConfigureService {
  private final String SYSTEM_CONFIGURE = "SYSTEM_CONFIGURE";
  private final String PASSWORD_STRATEGY = "password-strategy";

  @Autowired
  private IDictDataService dictDataService;


  @Override
  public List<DictData> getConfigure() {
    return dictDataService.getDictDataByType(SYSTEM_CONFIGURE);
  }

  @Override
  public PasswordStrategyVo getConfigureByValue(String value) {
    DictData dictData = dictDataService.getDictDataByTypeAndValue(SYSTEM_CONFIGURE, value);
    if (dictData == null) {
      throw new AppException("无效数据");
    }
    return JSON.parseObject(dictData.getExtend(), PasswordStrategyVo.class);
  }

  @Override
  public void savePasswordStrategy(PasswordStrategyVo passwordStrategy) {
    DictData dictData = dictDataService.getDictDataByTypeAndValue(SYSTEM_CONFIGURE, PASSWORD_STRATEGY);
    if (dictData == null) {
      throw new AppException("无效数据");
    }
    dictData.setExtend(JSON.toJSONString(passwordStrategy));
    dictDataService.updateById(dictData);
  }
}
