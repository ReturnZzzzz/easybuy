package com.a.easybuy.controller;

import com.a.easybuy.service.EmailService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("mail")
public class EmailController {
    private Logger logger = LoggerFactory.getLogger(EmailController.class);
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisTemplate redisTemplate;

    // 发送简单邮件
    @RequestMapping("/simple")
    public String sendSimpleEmail(String email) {
        logger.info("sendSimpleEmail email:" + email);
        String random = RandomStringUtils.randomAlphanumeric(5);
        redisTemplate.opsForValue().set(email, random,3, TimeUnit.MINUTES);
        emailService.sendSimpleMail(
                email,
                "邮箱验证码",
                "111"
        );
        return "简单邮件已发送";
    }

    // 发送HTML邮件
    @RequestMapping("/html")
    public String sendHtmlEmail() throws MessagingException {
        String content = "<html><body><h1 style='color:red'>HTML邮件测试</h1>" +
                "<p>这是HTML格式的邮件内容</p></body></html>";
        emailService.sendHtmlMail(
                "1290666022@qq.com",
                "测试HTML邮件",
                content
        );
        return "HTML邮件已发送";
    }

    // 发送带附件的邮件
    @RequestMapping("/attachment")
    public String sendAttachmentEmail() throws MessagingException {
        String filePath = "/path/to/your/file.pdf";
        emailService.sendAttachmentMail(
                "1290666022@qq.com",
                "测试附件邮件",
                "请查收附件",
                filePath
        );
        return "附件邮件已发送";
    }
}