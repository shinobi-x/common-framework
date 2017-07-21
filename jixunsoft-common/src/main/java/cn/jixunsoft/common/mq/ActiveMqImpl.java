package cn.jixunsoft.common.mq;

import java.io.Serializable;

import javassist.bytecode.annotation.ByteMemberValue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * ActiveMQ 消息发送接收实现类
 * 
 * @ClassName: ActiveMqImpl
 * @Description: TODO
 * @author leeon
 * @date 2014年5月13日 下午5:29:03
 * 
 */
public class ActiveMqImpl implements MessageManager {

    private static final Logger logger = Logger.getLogger(ActiveMqImpl.class);

    private JmsTemplate jmsTemplate;
    private Destination destination;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * 注意本方法只返回Objectmessage和TextMessage两种类型的数据
     * @return Object or null
     */
    @Override
    public Object receiveMsg() {
        try {
            Message message = jmsTemplate.receive(destination);
            if (message instanceof ObjectMessage) {

                return ((ObjectMessage) message).getObject();

            }
            if (message instanceof TextMessage) {

                return ((TextMessage) message).getText();
            }

            return null;

        } catch (JMSException e) {
            logger.error("ActiveMQ receive msg error: ");
            return null;
        }
    }

    /**
     * 本方法可以发送String或者序列化的Object
     */
    @Override
    public boolean sendMsg(final Serializable msg) {
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                public ObjectMessage createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(msg);
                }
            });
            return true;
        } catch (Exception ex) {
            logger.error("ActiveMQ send msg error: " + ex.getMessage());
            return false;
        }
    }

}
