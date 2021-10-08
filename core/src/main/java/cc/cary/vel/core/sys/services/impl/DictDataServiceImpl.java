package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.sys.entities.DictData;
import cc.cary.vel.core.sys.mapper.DictDataMapper;
import cc.cary.vel.core.sys.services.IDictDataService;
import cc.cary.vel.core.sys.vo.DictDataVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DictDataServiceImpl
 *
 * @author Cary
 * @date 2021/7/22
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {
  @Override
  public IPage<DictData> findForPage(QueryRequest request, Map<String, Object> params) {
    return this.baseMapper.findForPage(QueryUtils.buildPage(request, true), params);
  }

  @Override
  public void add(DictDataVo dictDataVo) {
    DictData d = getOne(new LambdaQueryWrapper<DictData>()
        .eq(DictData::getValue, dictDataVo.getValue())
        .eq(DictData::getType, dictDataVo.getType())
    );
    if (d != null) {
      throw new AppException("值重复");
    }
    DictData dictData = new DictData();
    BeanUtils.copyProperties(dictDataVo, dictData);
    save(dictData);
  }

  @Override
  public void update(DictDataVo dictDataVo) {
    DictData d = getOne(new LambdaQueryWrapper<DictData>()
        .eq(DictData::getValue, dictDataVo.getValue())
        .ne(DictData::getId, dictDataVo.getId())
        .eq(DictData::getType, dictDataVo.getType())
    );
    if (d != null) {
      throw new AppException("值重复");
    }
    DictData dictData = new DictData();
    BeanUtils.copyProperties(dictDataVo, dictData);
    updateById(dictData);
  }

  @Override
  public void delete(long id) {
    removeById(id);
  }

  @Override
  public List<DictData> getDictDataByType(String type) {
    return this.baseMapper.getDictDataByType(type);
  }

  @Override
  public Map<String, String> getDictDataMapByType(String type) {
    List<DictData> dictDataList = this.baseMapper.getDictDataByType(type);
    return dictDataList.stream().collect(Collectors.toMap(DictData::getValue, DictData::getLabel));
  }

  @Override
  public Map<String, Object> getDictDataMapByTypes(String types) {
    String[] typeArr = types.split(",");
    Map<String, Object> res = new HashMap<>(16);
    for (String type : typeArr) {
      List<DictData> dictDataList = this.baseMapper.getDictDataByType(type);
      Map<String, String> dictDataMap = dictDataList.stream().collect(Collectors.toMap(DictData::getValue, DictData::getLabel));
      res.put(type, dictDataMap);
    }
    return res;
  }

  @Override
  public DictData getDictDataByTypeAndValue(String type, String value) {
    return getOne(new LambdaQueryWrapper<DictData>()
        .eq(DictData::getType, type)
        .eq(DictData::getValue, value)
    );
  }
}
