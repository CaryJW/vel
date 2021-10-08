package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.sys.entities.DictData;
import cc.cary.vel.core.sys.vo.DictDataVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Cary
 * @date 2021/7/22
 */
public interface IDictDataService extends IService<DictData> {
  /**
   * 分页查询
   *
   * @param request
   * @param params
   * @return IPage<AdminUser>
   */
  IPage<DictData> findForPage(@RequestBody QueryRequest request, Map<String, Object> params);

  /**
   * 添加
   *
   * @param dictDataVo
   */
  void add(DictDataVo dictDataVo);

  /**
   * 更新
   *
   * @param dictDataVo
   */
  void update(DictDataVo dictDataVo);

  /**
   * 删除
   *
   * @param id
   */
  void delete(long id);

  /**
   * 获取字典数据
   *
   * @param type
   * @return
   */
  List<DictData> getDictDataByType(String type);

  /**
   * 获取字典数据
   *
   * @param type
   * @return
   */
  Map<String, String> getDictDataMapByType(String type);

  /**
   * 获取字典数据
   *
   * @param types
   * @return
   */
  Map<String, Object> getDictDataMapByTypes(String types);


  /**
   * 根据类型和值获取字典数据
   *
   * @param type
   * @param value
   * @return
   */
  DictData getDictDataByTypeAndValue(String type, String value);
}
