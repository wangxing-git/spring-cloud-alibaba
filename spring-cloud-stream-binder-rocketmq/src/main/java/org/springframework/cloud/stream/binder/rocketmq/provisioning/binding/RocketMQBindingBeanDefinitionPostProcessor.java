package org.springframework.cloud.stream.binder.rocketmq.provisioning.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.cloud.stream.binder.rocketmq.annotation.RocketMQBinding;
import org.springframework.cloud.stream.binder.rocketmq.provisioning.binding.registry.RocketMQBindingRegistry;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * @author wangxing
 * @create 2019/5/27
 */
public class RocketMQBindingBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {

    private RocketMQBindingRegistry rocketMQBindingRegistry;

    private Logger logger = LoggerFactory.getLogger(RocketMQBindingBeanDefinitionPostProcessor.class);

    public RocketMQBindingBeanDefinitionPostProcessor(RocketMQBindingRegistry rocketMQBindingRegistry) {
        this.rocketMQBindingRegistry = rocketMQBindingRegistry;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        RocketMQBinding rocketMQBinding = AnnotatedElementUtils.findMergedAnnotation(beanType, RocketMQBinding.class);
        if (rocketMQBinding == null) {
            Method resolvedFactoryMethod = beanDefinition.getResolvedFactoryMethod();
            if (resolvedFactoryMethod != null) {
                rocketMQBinding = AnnotatedElementUtils.findMergedAnnotation(resolvedFactoryMethod, RocketMQBinding.class);
            }
        }
        if (rocketMQBinding != null) {
            if (rocketMQBinding.bindingName().length > 0) {
                rocketMQBindingRegistry.registerRocketMQBinding(beanName, beanType, rocketMQBinding);
            } else {
                logger.warn("the annotation RocketMQBinding has been annotated in bean '{}',but the bindingName property not set! Will be ignored.", beanName);
            }
        }
    }

}