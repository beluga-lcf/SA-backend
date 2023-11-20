package com.example.genius.service.impl;

import com.example.genius.entity.Mail;
import com.example.genius.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Slf4j // 直接使用log
@Service
public class EmailServiceImpl implements EmailService {
    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    public void checkMailNotNull(Mail mail) {
        Assert.notNull(mail,"邮件不能为空");
        Assert.notNull(mail.getSendTo(), "邮件收件人不能为空");
        Assert.notNull(mail.getSubject(), "邮件主题不能为空");
    }

    @Override
    public void sendRegisterVerifyMail(String customMail, String verifyCode) {
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(sendMailer);
        simpleMail.setTo(customMail);
        simpleMail.setSubject("注册验证");
        simpleMail.setText("您的验证码是："+verifyCode);
        simpleMail.setSentDate(new Date());
        javaMailSender.send(simpleMail);
        log.info("发送注册验证邮件成功:{}->{}",sendMailer,simpleMail.getTo());
    }

    @Override
    public void sendSimpleMail(Mail Mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMailNotNull(Mail);
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(Mail.getSendTo().split(","));
        //邮件主题
        message.setSubject(Mail.getSubject());
        //邮件内容
        message.setText(Mail.getText());
        //邮件发送时间
        message.setSentDate(new Date());
        javaMailSender.send(message);
        log.info("发送邮件成功:{}->{}",sendMailer,Mail.getSendTo());
    }


    @Override
    public void sendHtmlMail(Mail Mail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        checkMailNotNull(Mail);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            //邮件发件人
            helper.setFrom(sendMailer);
            //邮件收件人 1或多个
            helper.setTo(Mail.getSendTo().split(","));
            //邮件主题
            helper.setSubject(Mail.getSubject());
            //邮件内容
            helper.setText(Mail.getText(),true);
            //邮件发送时间
            helper.setSentDate(new Date());
            String filePath = Mail.getFilePath();
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName,file);
            }
            javaMailSender.send(message);
            log.info("发送邮件成功:{}->{}",sendMailer,Mail.getSendTo());

        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！",e);
        }
    }
}

