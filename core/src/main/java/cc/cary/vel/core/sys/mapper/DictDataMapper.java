package cc.cary.vel.core.sys.mapper;

import cc.cary.vel.core.sys.entities.DictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DictDataMapper
 *
 * @author Cary
 * @date 2021/7/22
 */
public interface DictDataMapper extends BaseMapper<DictData> {
  /**
   * 分页查询
   *
   * @param page
   * @param params
   * @return IPage<AdminUser>
   */
  IPage<DictData> findForPage(Page<?> page, @Param("params") Map<String, Object> params);

  /**
   * 获取字典数据
   *
   * @param type
   * @return
   */
  List<DictData> getDictDataByType(@Param("type") String type);
}
