package com.hyles.shuimen.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * created by 13461 on 2023/6/20
 */
@Component
public class MailComponent {
    @Autowired
    private JavaMailSender mailSender;
    public void send(String target,String code){
        System.out.println("发送邮件");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("来自-水门外卖-的邮件");
        message.setText("验证码："+ code);

        message.setTo(target);
        message.setFrom("2958671279@qq.com");

        mailSender.send(message);

    }
    //发送复杂邮件，带网页和图片
    public void send2() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("来自yz的邮件");
        helper.setText("这是正文");

        helper.setTo("2958671279yz@gmail.com");
        helper.setFrom("2958671279@qq.com");

        // 添加附件
        FileSystemResource file = new FileSystemResource("C:/wallpaper.jpg");
        helper.addAttachment("附件.jpg", file);

        helper.setText("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>菜鸟教程(runoob.com)</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>我的第一个标题</h1>\n" +
                "    <p>我的第一个段落。</p>\n" +
                "</body>\n" +
                "</html>",true);
        mailSender.send(message);
    }
}
