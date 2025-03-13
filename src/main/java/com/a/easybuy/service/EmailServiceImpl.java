package com.a.easybuy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文本邮件
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1290666022@qq.com"); // 必须与配置的username一致
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送HTML格式邮件
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("1807280694@qq.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // true表示支持html

        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     */
    public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("1807280694@qq.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        // 添加附件
        File file = new File(filePath);
        helper.addAttachment(file.getName(), file);

        mailSender.send(message);
    }

    /**
     * 发送带内联资源的邮件（如图片）
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("1807280694@qq.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        // 添加内联图片
        File file = new File(rscPath);
        helper.addInline(rscId, file);

        mailSender.send(message);
    }
}