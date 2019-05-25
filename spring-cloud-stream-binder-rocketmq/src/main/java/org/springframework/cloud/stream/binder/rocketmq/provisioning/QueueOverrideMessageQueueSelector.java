package org.springframework.cloud.stream.binder.rocketmq.provisioning;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.rocketmq.RocketMQBinderConstants;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wangxing
 * @create 2019/5/25
 */
public class QueueOverrideMessageQueueSelector implements MessageQueueSelector {

    private static final Logger logger = LoggerFactory.getLogger(QueueOverrideMessageQueueSelector.class);

    private MessageQueueSelector delegate;

    public QueueOverrideMessageQueueSelector(MessageQueueSelector delegate) {
        Assert.notNull(delegate, "delegate must not be null");
        this.delegate = delegate;
    }

    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        String override = msg.getProperty(RocketMQBinderConstants.ROCKET_QUEUE_INDEX_OVERRIDE);
        if (StringUtils.isNotBlank(override)) {
            int queueIndex = 0;
            try {
                queueIndex = Integer.parseInt(override);
            } catch (NumberFormatException nfe) {
                logger.warn("the '" + RocketMQBinderConstants.ROCKET_QUEUE_INDEX_OVERRIDE + "' header is not a int number! use the default value of 0.");
            }
            return mqs.get(queueIndex % mqs.size());
        }
        return delegate.select(mqs, msg, arg);
    }

}