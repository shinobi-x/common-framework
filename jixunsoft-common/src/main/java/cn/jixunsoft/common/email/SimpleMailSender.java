package cn.jixunsoft.common.email;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件发送器
 * 
 * @author Danfo Yam
 *
 * 2014年1月13日
 *
 */
public class SimpleMailSender implements MailSender {

    /**
     * java mail sender
     */
    private JavaMailSender javaMailSender;

    /**
     * @param javaMailSender the javaMailSender to set
     */
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送html格式邮件
     */
    public void sendHtmlMessage(SimpleMail simpleMail) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

        if (!StringUtils.isBlank(simpleMail.getTo())) {
            helper.setTo(simpleMail.getTo());
        } else {
            helper.setTo(simpleMail.getTos());
        }

        helper.setFrom(simpleMail.getFrom());
        helper.setSubject(simpleMail.getSubject());

        helper.setText(simpleMail.getContent(), true);

        // 增加附件
        if (MapUtils.isNotEmpty(simpleMail.getAttachFileMap())) {

            Map<String, File> attachFileMap = simpleMail.getAttachFileMap();

            // 循环添加附件
            for (Entry<String, File> entry : attachFileMap.entrySet()) {
                String attachFileName = entry.getKey();
                File attachFile = entry.getValue();
                helper.addAttachment(attachFileName, attachFile);
            }
        }

        javaMailSender.send(msg);
    }

    /**
     * 发送文本格式邮件
     */
    @Override
    public void sendTextMessage(SimpleMail simpleMail) throws Throwable {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

        if (!StringUtils.isBlank(simpleMail.getTo())) {
            helper.setTo(simpleMail.getTo());
        } else {
            helper.setTo(simpleMail.getTos());
        }

        helper.setFrom(simpleMail.getFrom());
        helper.setSubject(simpleMail.getSubject());

        helper.setText(simpleMail.getContent(), true);

        // 增加附件
        if (MapUtils.isEmpty(simpleMail.getAttachFileMap())) {

            Map<String, File> attachFileMap = simpleMail.getAttachFileMap();

            // 循环添加附件
            for (Entry<String, File> entry : attachFileMap.entrySet()) {
                String attachFileName = entry.getKey();
                File attachFile = entry.getValue();
                helper.addAttachment(attachFileName, attachFile);
            }
        }

        javaMailSender.send(msg);
    }


}
