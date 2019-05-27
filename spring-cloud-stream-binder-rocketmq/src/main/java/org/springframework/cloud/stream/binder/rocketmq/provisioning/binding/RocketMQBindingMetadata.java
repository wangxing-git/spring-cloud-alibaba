package org.springframework.cloud.stream.binder.rocketmq.provisioning.binding;

/**
 * @author wangxing
 * @create 2019/5/27
 */
public class RocketMQBindingMetadata {

    private String bindingName;

    private String messageQueueSelectorName;

    public String getMessageQueueSelectorName() {
        return messageQueueSelectorName;
    }

    public void setMessageQueueSelectorName(String messageQueueSelectorName) {
        this.messageQueueSelectorName = messageQueueSelectorName == null ? null : messageQueueSelectorName.trim();
    }

    public String getBindingName() {
        return bindingName;
    }

    public void setBindingName(String bindingName) {
        this.bindingName = bindingName == null ? null : bindingName.trim();
    }

}