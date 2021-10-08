package cc.cary.vel.adminapi.controllers.sys;

import cc.cary.vel.core.common.entities.UploadResult;
import cc.cary.vel.core.common.libs.ResultData;
import cc.cary.vel.core.common.upload.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * UploadController
 *
 * @author Cary
 * @date 2021/05/22
 */
@RestController
@RequestMapping("/file-operation")
public class UploadController {
  @Autowired
  private UploadUtil uploadUtil;

  @PostMapping("/upload")
  public ResultData upload(@Valid MultipartFile file) {
    UploadResult uploadResult;
    try {
      uploadResult = uploadUtil.upload(file);
    } catch (Exception e) {
      return ResultData.fail("文件上传失败");
    }
    return ResultData.ok().put("uploadResult", uploadResult);
  }

  @GetMapping("/delete")
  public ResultData delete(@RequestParam String url) {
    uploadUtil.delete(url);
    return ResultData.ok();
  }
}
