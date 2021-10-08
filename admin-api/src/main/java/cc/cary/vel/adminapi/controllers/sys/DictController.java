package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.core.common.annotation.Log;
import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.sys.services.IDictDataService;
import cc.cary.vel.core.sys.services.IDictTypeService;
import cc.cary.vel.core.sys.vo.DictDataVo;
import cc.cary.vel.core.sys.vo.DictTypeVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * DictController
 *
 * @author Cary
 * @date 2021/7/22
 */
@RestController
@RequestMapping("/dict")
@Validated
public class DictController {
  @Autowired
  private IDictTypeService dictTypeService;
  @Autowired
  private IDictDataService dictDataService;

  @GetMapping("/list")
  @RequiresPermissions("dict:list")
  public ResultData list(QueryRequest request, @RequestParam Map<String, Object> params) {
    return ResultData.ok().put(dictTypeService.findForPage(request, params));
  }

  @PostMapping("/add")
  @Log("添加字典类型")
  @RequiresPermissions("dict:add")
  public ResultData add(@Valid DictTypeVo dictTypeVo) {
    dictTypeService.add(dictTypeVo);
    return ResultData.ok();
  }

  @PostMapping("/update")
  @Log("修改字典类型")
  @RequiresPermissions("dict:update")
  public ResultData update(@Valid DictTypeVo dictTypeVo) {
    dictTypeService.update(dictTypeVo);
    return ResultData.ok();
  }

  @GetMapping("/delete/{id}")
  @Log("删除字典类型")
  @RequiresPermissions("dict:delete")
  public ResultData delete(@PathVariable long id) {
    dictTypeService.delete(id);
    return ResultData.ok();
  }

  @GetMapping("/data/list")
  @RequiresPermissions("dict:data-list")
  public ResultData dataList(QueryRequest request, @RequestParam Map<String, Object> params) {
    return ResultData.ok().put(dictDataService.findForPage(request, params));
  }

  @PostMapping("/data/add")
  @Log("添加字典数据")
  @RequiresPermissions("dict:data-add")
  public ResultData dataAdd(@Valid DictDataVo dictDataVo) {
    dictDataService.add(dictDataVo);
    return ResultData.ok();
  }

  @PostMapping("/data/update")
  @Log("修改字典数据")
  @RequiresPermissions("dict:data-update")
  public ResultData dataUpdate(@Valid DictDataVo dictDataVo) {
    dictDataService.update(dictDataVo);
    return ResultData.ok();
  }

  @GetMapping("/data/delete/{id}")
  @Log("删除字典数据")
  @RequiresPermissions("dict:data-delete")
  public ResultData dataDelete(@PathVariable long id) {
    dictDataService.delete(id);
    return ResultData.ok();
  }

  @GetMapping("/data/{types}")
  public ResultData dictDataByType(@PathVariable String types) {
    return ResultData.ok().put("dictData", dictDataService.getDictDataMapByTypes(types));
  }

  @GetMapping("/data/{type}/value/{value}")
  public ResultData dictDataByTypeAndValue(@PathVariable String type, @PathVariable String value) {
    return ResultData.ok().put("dictData", dictDataService.getDictDataByTypeAndValue(type, value));
  }

}
