package cn.jixunsoft.common.email;

import java.io.File;
import java.io.Serializable;
import java.util.Map;


/**
 * 发送邮件抽象类
 * 
 * @author Danfo Yam
 *
 * 2014年1月13日
 *
 */
public class SimpleMail implements Serializable {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 6516660928876002486L;

    /**
     * 邮件目标
     */
    private String to;

    /**
     * 邮件目标数组
     */
    private String[] tos;

    /**
     * 邮件来源
     */
    private String from;

    /**
     * 主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件列表
     */
    private Map<String, File> attachFileMap;

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the tos
     */
    public String[] getTos() {
        return tos;
    }

    /**
     * @param tos the tos to set
     */
    public void setTos(String[] tos) {
        this.tos = tos;
    }

    /**
     * @return the attachFileMap
     */
    public Map<String, File> getAttachFileMap() {
        return attachFileMap;
    }

    /**
     * @param attachFileMap the attachFileMap to set
     */
    public void setAttachFileMap(Map<String, File> attachFileMap) {
        this.attachFileMap = attachFileMap;
    }
}
