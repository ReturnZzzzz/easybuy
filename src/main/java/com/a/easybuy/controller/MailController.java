package com.a.easybuy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("mail")
public class MailController {

    private Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("send")
    public void sendMail() {
        //创建简单的邮件发送对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("1290666022@qq.com");            // 设置收件人邮箱
        message.setSubject("我是秦始皇");                  // 设置邮件主题
        message.setText("V我50祝我东山再起"); // 设置邮件文本内容
        message.setSentDate(new Date());                // 设置邮件发送时间
        //发送
        mailSender.send(message);
    }
}
