package cn.jixunsoft.common.mq;

import java.io.Serializable;

import javax.jms.Message;

/**
 * 
 * 消息队列接口定义
 * 
 * @ClassName: MqManager
 * @Description: TODO
 * @author leeon
 * @date 2014年5月13日 下午5:30:04
 * 
 */
public interface MessageManager {

    /**
     * 接收消息
     * 
     * @Title: receiveMsg
     * @Description: TODO
     * @param @return
     * @return Message
     * @throws
     */
    public Object receiveMsg();

    /**
     * 发送消息
     * 
     * @Title: sendMsg
     * @Description: TODO
     * @param @param msg
     * @param @return
     * @return boolean
     * @throws
     */
    public boolean sendMsg(Serializable msg);
}
