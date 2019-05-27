package org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.registry.processor;

import org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.RocketMQBindingMetadata;

/**
 * @author wangxing
 * @create 2019/5/27
 */
public interface RocketMQBindingRegistryProcessor {

    boolean support(Class beanType);

    void process(String beanName, RocketMQBindingMetadata rocketMQBindingMetadata);

}