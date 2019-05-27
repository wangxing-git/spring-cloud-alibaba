package org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.registry.processor;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.RocketMQBindingMetadata;

/**
 * @author wangxing
 * @create 2019/5/27
 */
public class MessageQueueSelectorRegistryProcessor implements RocketMQBindingRegistryProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageQueueSelectorRegistryProcessor.class);

    @Override
    public boolean support(Class beanType) {
        return MessageQueueSelector.class.isAssignableFrom(beanType);
    }

    @Override
    public void process(String beanName, RocketMQBindingMetadata rocketMQBindingMetadata) {
        if (rocketMQBindingMetadata.getMessageQueueSelectorName() != null) {
            logger.warn("Override MessageQueueSelector bean name '{}' to '{}'", rocketMQBindingMetadata.getMessageQueueSelectorName(), beanName);
        }
        rocketMQBindingMetadata.setMessageQueueSelectorName(beanName);
    }

}