package org.springframework.cloud.stream.binder.rocketmq.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangxing
 * @create 2019/5/27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RocketMQBinding {

    @AliasFor("bindingName")
    String[] value() default {};

    /**
     * the name of bindings.
     *
     * @return
     */
    @AliasFor("value")
    String[] bindingName() default {};

}