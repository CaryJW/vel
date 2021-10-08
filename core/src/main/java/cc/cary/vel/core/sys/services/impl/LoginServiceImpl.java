package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.business.entities.User;
import cc.cary.vel.core.business.services.IUserService;
import cc.cary.vel.core.common.libs.AppException;
import cc.cary.vel.core.common.libs.Constants;
import cc.cary.vel.core.common.libs.RedisKeys;
import cc.cary.vel.core.common.utils.*;
import cc.cary.vel.core.monitor.entities.LoginLog;
import cc.cary.vel.core.monitor.services.ILoginLogService;
import cc.cary.vel.core.sys.entities.AdminUser;
import cc.cary.vel.core.sys.services.IAdminUserService;
import cc.cary.vel.core.sys.services.IConfigureService;
import cc.cary.vel.core.sys.services.ILoginService;
import cc.cary.vel.core.sys.vo.LoginVo;
import cc.cary.vel.core.sys.vo.PasswordStrategyVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * LoginServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class LoginServiceImpl implements ILoginService {
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private RedisUtils redisUtils;
  @Autowired
  private IAdminUserService adminUserService;
  @Autowired
  private ILoginLogService loginLogService;
  @Autowired
  private IUserService userService;
  @Autowired
  private IConfigureService configureService;

  @Override
  public String adminLogin(LoginVo loginVo) {
    Object code = redisUtils.get(loginVo.getKey());
    if (code == null) {
      throw new AppException("验证码已过期");
    }
    if (!StringUtils.endsWithIgnoreCase((String) code, loginVo.getCaptcha())) {
      throw new AppException("验证码错误");
    }

    AdminUser user = adminUserService.findByUserName(loginVo.getUsername());
    String password = DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes());
    if (user == null) {
      throw new AppException("账户不存在");
    }
    if (user.getStatus() == Constants.ADMIN_USER_STATUS_LOCK) {
      this.checkUnlockTime(user);
    }
    if (!StringUtils.equals(password, user.getPassword())) {
      this.checkLockLimit(user);
    }
    // 生成koen并存入reids
    String token = jwtUtils.generateToken(String.valueOf(user.getId()));
    redisUtils.hmSet(RedisKeys.USER_LOGIN, String.valueOf(user.getId()), token);

    user.setLoginTime(LocalDateTime.now().toString());
    adminUserService.updateById(user);

    // 保存登录日志
    HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
    Map<String, String> info = VelUtils.getSystemBrowserInfo();
    String ip = IpUtils.getIpAddr(request);

    LoginLog loginLog = new LoginLog();
    loginLog.setUsername(user.getUsername());
    loginLog.setBrowser(info.get("browser"));
    loginLog.setSystem(info.get("system"));
    loginLog.setIp(ip);
    loginLog.setLocation(AddressUtils.getCityInfo(ip));
    loginLog.setLoginTime(LocalDateTime.now().toString());

    loginLogService.save(loginLog);

    redisUtils.remove(loginVo.getKey());
    return token;
  }

  @Override
  public String userLogin(String username, String password) {
    User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    password = DigestUtils.md5DigestAsHex(password.getBytes());
    if (user == null || !StringUtils.equals(password, user.getPassword())) {
      throw new AppException("账号或密码错误");
    }
    if (user.getStatus() == Constants.USER_STATUS_LOCK) {
      throw new AppException("账号已被锁定,请联系管理员");
    }
    // 生成koen并存入reids
    String token = jwtUtils.generateToken(String.valueOf(user.getId()));
    redisUtils.hmSet(RedisKeys.API_AUTHORIZED, String.valueOf(user.getId()), token);

    return token;
  }

  @Override
  public String captcha(String key) {
    SpecCaptcha specCaptcha = new SpecCaptcha(150, 48, 4);
    String verCode = specCaptcha.text().toLowerCase();
    redisUtils.set(key, verCode, 60L, TimeUnit.SECONDS);
    return specCaptcha.toBase64();
  }

  private void checkLockLimit(AdminUser user) {
    final String passwordStrategyStr = "password-strategy";
    PasswordStrategyVo passwordStrategy = configureService.getConfigureByValue(passwordStrategyStr);

    String key = RedisKeys.USER_LOCK_LIMIT + user.getId();
    long limit = redisUtils.increment(key, 1L);
    if (limit == 1L) {
      redisUtils.expire(key, passwordStrategy.getFailLoginTime(), getTimeType(passwordStrategy.getFailLoginTimeType()));
    }
    if (limit >= passwordStrategy.getFailLoginCount()) {
      LocalDateTime unlockTime = getUnlockTime(passwordStrategy.getUnlockTimeType(), passwordStrategy.getUnlockTime());
      user.setUnlockTime(unlockTime.toString());
      user.setStatus(Constants.ADMIN_USER_STATUS_LOCK);
      adminUserService.updateById(user);
      throw new AppException("账号已被锁定,请联系管理员");
    }

    throw new AppException(String.format("密码错误，您还有%s次机会重试！", passwordStrategy.getFailLoginCount() - limit));
  }

  private TimeUnit getTimeType(int type) {
    switch (type) {
      case 0:
        return TimeUnit.HOURS;
      case 1:
        return TimeUnit.MINUTES;
      default:
        return TimeUnit.SECONDS;
    }
  }

  private LocalDateTime getUnlockTime(int type, long time) {
    switch (type) {
      case 0:
        return LocalDateTime.now().plusHours(time);
      case 1:
        return LocalDateTime.now().plusMinutes(time);
      default:
        return LocalDateTime.now().plusSeconds(time);
    }
  }

  private void checkUnlockTime(AdminUser user) {
    if (!StringUtils.isEmpty(user.getUnlockTime()) &&
        VelDateUtils.parseToLocalDateTime(user.getUnlockTime()).isBefore(LocalDateTime.now())
    ) {
      user.setStatus(Constants.USER_STATUS_NORMAL);
      return;
    }

    throw new AppException("账号已被锁定,请联系管理员");
  }
}
