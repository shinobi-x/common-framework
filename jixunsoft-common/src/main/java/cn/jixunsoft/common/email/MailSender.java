package cn.jixunsoft.common.email;

/**
 * 邮件发送器
 * 
 * @author Danfo Yam
 *
 * 2014年1月13日
 *
 */
public interface MailSender {

    /**
     * 发送html格式邮件
     * 
     * @throws Throwable
     */
    public void sendHtmlMessage(SimpleMail simpleMail) throws Throwable;

    /**
     * 发送text格式邮件
     * 
     * @throws Throwable
     */
    public void sendTextMessage(SimpleMail simpleMail) throws Throwable;
}
