package cc.cary.vel.core.sys.mapper;

import cc.cary.vel.core.sys.entities.DictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * DictTypeMapper
 *
 * @author Cary
 * @date 2021/7/22
 */
public interface DictTypeMapper extends BaseMapper<DictType> {
  /**
   * 分页查询
   *
   * @param page
   * @param params
   * @return IPage<AdminUser>
   */
  IPage<DictType> findForPage(Page<?> page, @Param("params") Map<String, Object> params);
}
