package cc.cary.vel.adminapi.controllers.business;

import cc.cary.vel.core.business.entities.Tinymce;
import cc.cary.vel.core.business.services.ITinymceService;
import cc.cary.vel.core.common.libs.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * OtherController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/other")
public class OtherController {
  @Autowired
  private ITinymceService tinymceService;

  @GetMapping("/get-tinymce")
  public ResultData getTinymce() {
    return ResultData.ok().put("content", tinymceService.getById(1L).getContent());
  }

  @PostMapping("/save-tinymce")
  public ResultData saveTinymce(@RequestParam String content) {
    Tinymce tinymce = tinymceService.getById(1L);
    tinymce.setContent(content);
    tinymceService.updateById(tinymce);
    return ResultData.ok();
  }
}
