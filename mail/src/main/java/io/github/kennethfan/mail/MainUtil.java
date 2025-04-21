package io.github.kennethfan.mail;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.URLDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Properties;

@Slf4j
public class MainUtil {
    public static void main(String[] args) {
        final String username = "<sender>";
        final String password = "<sender password>"; // 或应用专用密码

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // 使用TLS
//        props.put("mail.smtp.host", "smtp.126.com");
//        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("<receiver>"));
            message.setSubject("测试邮件主题");
            Multipart multipart = new MimeMultipart();
            message.setContent(multipart);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String htmlContent = "<h1>HTML内容邮件</h1><p>这是一封<strong>HTML格式</strong>的邮件</p>";
            messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            String filePath = "http://example.com/path/to/file";
            DataSource source = new URLDataSource(new URL(filePath));
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("document.pdf");
            multipart.addBodyPart(attachmentPart);

            Transport.send(message);
            log.info("邮件发送成功！");
        } catch (Exception e) {
            log.error("邮件发送失败, ", e);
        }
    }
}