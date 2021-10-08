package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.sys.entities.DictType;
import cc.cary.vel.core.sys.vo.DictTypeVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * IDictTypeService
 *
 * @author Cary
 * @date 2021/7/22
 */
public interface IDictTypeService extends IService<DictType> {
  /**
   * 分页查询
   *
   * @param request
   * @param params
   * @return IPage<AdminUser>
   */
  IPage<DictType> findForPage(@RequestBody QueryRequest request, Map<String, Object> params);

  /**
   * 添加
   *
   * @param dictTypeVo
   */
  void add(DictTypeVo dictTypeVo);

  /**
   * 更新
   *
   * @param dictTypeVo
   */
  void update(DictTypeVo dictTypeVo);

  /**
   * 删除
   *
   * @param id
   */
  void delete(long id);
}
