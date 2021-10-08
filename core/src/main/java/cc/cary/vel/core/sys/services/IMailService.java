package cc.cary.vel.core.sys.services;

import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.Map;

/**
 * 邮件服务
 *
 * @author Cary
 * @date 2021/8/13
 */
public interface IMailService {

  /**
   * 发送简单内容邮件
   *
   * @param addressee 收件人
   * @param subject   主题
   * @param content   内容
   */
  void sendSimpleMail(String addressee, String subject, String content);

  /**
   * 发送html内容邮件
   *
   * @param addressee 收件人
   * @param subject   主题
   * @param content   内容
   */
  void sendHtmlMail(String addressee, String subject, String content);

  /**
   * 发送带附件的邮件
   *
   * @param addressee 收件人
   * @param subject   主题
   * @param content   内容
   * @param file      附件
   */
  void sendAttachmentsMail(String addressee, String subject, String content, File file);

  /**
   * 发送带静态资源的邮件
   *
   * @param addressee 收件人
   * @param subject   主题
   * @param content   内容
   * @param rsc       静态资源
   */
  void sendInlineResourceMail(String addressee, String subject, String content, Map<String, File> rsc);

  /**
   * 异步发送验证码
   *
   * @param addressee  收件人
   * @param subject    主题
   * @param verifyCode 验证码
   */
  @Async("velAsyncThreadPool")
  void sendCaptcha(String addressee, String subject, String verifyCode);
}
