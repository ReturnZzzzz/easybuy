package com.a.easybuy.service;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendSimpleMail(String to, String subject, String content);
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException;
    public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException;
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException;
}
