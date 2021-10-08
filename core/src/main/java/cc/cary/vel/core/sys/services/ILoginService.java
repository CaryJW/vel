package cc.cary.vel.core.sys.services;

import cc.cary.vel.core.sys.vo.LoginVo;

/**
 * ILoginService
 *
 * @author Cary
 * @date 2021/05/22
 */
public interface ILoginService {
  /**
   * admin登录
   *
   * @param loginVo
   * @return
   */
  String adminLogin(LoginVo loginVo);

  /**
   * user登录
   *
   * @param username
   * @param password
   * @return
   */
  String userLogin(String username, String password);

  /**
   * 验证码
   *
   * @param key
   * @return
   */
  String captcha(String key);
}
