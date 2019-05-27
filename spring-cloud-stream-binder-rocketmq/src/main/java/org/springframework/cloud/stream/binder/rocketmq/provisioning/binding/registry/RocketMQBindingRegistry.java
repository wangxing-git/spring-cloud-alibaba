package org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.registry;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.stream.binder.rocketmq.annotation.RocketMQBinding;
import org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.RocketMQBindingMetadata;
import org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.registry.processor.RocketMQBindingRegistryProcessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangxing
 * @create 2019/5/27
 */
public class RocketMQBindingRegistry {

    private List<RocketMQBindingRegistryProcessor> rocketMQBindingRegistries;

    private Map<String, RocketMQBindingMetadata> rocketMqBindingMetadataMap = Maps.newConcurrentMap();

    public RocketMQBindingRegistry(ObjectProvider<RocketMQBindingRegistryProcessor> rocketMQBindingRegistries) {
        this.rocketMQBindingRegistries = rocketMQBindingRegistries.orderedStream().collect(Collectors.toList());
    }

    public void registerRocketMQBinding(String beanName, Class beanType, RocketMQBinding rocketMQBinding) {
        Stream.of(rocketMQBinding.bindingName())
                .forEach(bindingName -> {
                    RocketMQBindingMetadata rocketMQBindingMetadata = rocketMqBindingMetadataMap.get(beanName);
                    if (rocketMQBindingMetadata == null) {
                        rocketMQBindingMetadata = new RocketMQBindingMetadata();
                        rocketMQBindingMetadata.setBindingName(bindingName);
                    }
                    RocketMQBindingMetadata finalRocketMQBindingMetadata = rocketMQBindingMetadata;
                    rocketMQBindingRegistries.stream()
                            .filter(rocketMQBindingRegistryProcessor -> rocketMQBindingRegistryProcessor.support(beanType))
                            .forEach(rocketMQBindingRegistryProcessor -> {
                                rocketMQBindingRegistryProcessor.process(beanName, finalRocketMQBindingMetadata);
                            });
                    rocketMqBindingMetadataMap.putIfAbsent(bindingName, finalRocketMQBindingMetadata);
                });
    }

    public RocketMQBindingMetadata getMetadata(String bindingName) {
        return rocketMqBindingMetadataMap.get(bindingName);
    }

}