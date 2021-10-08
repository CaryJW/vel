package cc.cary.vel.core.sys.services.impl;

import cc.cary.vel.core.sys.services.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * 邮件服务
 *
 * @author Cary
 * @date 2021/8/13
 */
@Service
@Slf4j
public class MailServiceImpl implements IMailService {
  @Autowired
  private JavaMailSender mailSender;
  @Value("${spring.mail.username}")
  private String emailSender;
  @Autowired
  TemplateEngine templateEngine;

  @Override
  public void sendSimpleMail(String addressee, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailSender);
    message.setTo(addressee);
    message.setSubject(subject);
    message.setText(content);
    try {
      mailSender.send(message);
    } catch (MailException me) {
      log.error("[邮件服务]邮件发送失败：", me);
    }
  }

  @Override
  public void sendHtmlMail(String addressee, String subject, String content) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(emailSender);
      helper.setTo(addressee);
      helper.setSubject(subject);
      helper.setText(content, true);
      mailSender.send(message);
    } catch (MessagingException me) {
      log.error("[邮件服务]邮件发送失败：", me);
    }
  }

  @Override
  public void sendAttachmentsMail(String addressee, String subject, String content, File file) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(emailSender);
      helper.setTo(addressee);
      helper.setSubject(subject);
      helper.setText(content, true);
      helper.addAttachment(file.getName(), file);
      mailSender.send(message);
    } catch (MessagingException me) {
      log.error("[邮件服务]邮件发送失败：", me);
    }
  }

  @Override
  public void sendInlineResourceMail(String addressee, String subject, String content, Map<String, File> rsc) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(emailSender);
      helper.setTo(addressee);
      helper.setSubject(subject);
      helper.setText(content, true);
      for (String rscId : rsc.keySet()) {
        helper.addInline(rscId, rsc.get(rscId));
      }
      mailSender.send(message);
    } catch (MessagingException me) {
      log.error("[邮件服务]邮件发送失败：", me);
    }
  }

  @Override
  public void sendCaptcha(String addressee, String subject, String verifyCode) {
    // 创建邮件正文
    Context context = new Context();
    context.setVariable("verifyCode", verifyCode);
    // 将模块引擎内容解析成html字符串
    String emailContent = templateEngine.process("captchaEmailTemplate", context);
    // 发送html格式邮件
    sendHtmlMail(addressee, subject, emailContent);
  }
}
