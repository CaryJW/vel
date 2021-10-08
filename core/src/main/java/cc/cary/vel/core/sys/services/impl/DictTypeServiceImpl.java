package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.sys.entities.DictData;
import cc.cary.vel.core.sys.entities.DictType;
import cc.cary.vel.core.sys.mapper.DictTypeMapper;
import cc.cary.vel.core.sys.services.IDictDataService;
import cc.cary.vel.core.sys.services.IDictTypeService;
import cc.cary.vel.core.sys.vo.DictTypeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DictServiceImpl
 *
 * @author Cary
 * @date 2021/7/22
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {
  @Autowired
  private IDictDataService dictDataService;

  @Override
  public IPage<DictType> findForPage(QueryRequest request, Map<String, Object> params) {
    return this.baseMapper.findForPage(QueryUtils.buildPage(request, true), params);
  }

  @Override
  public void add(DictTypeVo dictTypeVo) {
    DictType d = getOne(new LambdaQueryWrapper<DictType>().eq(DictType::getType, dictTypeVo.getType()));
    if (d != null) {
      throw new AppException("类型重复");
    }
    DictType dictType = new DictType();
    BeanUtils.copyProperties(dictTypeVo, dictType);
    save(dictType);
  }

  @Override
  public void update(DictTypeVo dictTypeVo) {
    DictType d = getOne(new LambdaQueryWrapper<DictType>()
        .eq(DictType::getType, dictTypeVo.getType())
        .ne(DictType::getId, dictTypeVo.getId())
    );
    if (d != null) {
      throw new AppException("类型重复");
    }
    DictType dictType = new DictType();
    BeanUtils.copyProperties(dictTypeVo, dictType);
    updateById(dictType);
  }

  @Override
  public void delete(long id) {
    DictType dictType = getById(id);
    if (dictType == null) {
      throw new AppException("无效ID");
    }
    List<DictData> dictDataList = dictDataService.list(new LambdaQueryWrapper<DictData>().eq(DictData::getType, dictType.getType()));
    if (dictDataList.size() > 0) {
      throw new AppException("该字典下有数据，不能删除");
    }
    removeById(id);
  }
}
