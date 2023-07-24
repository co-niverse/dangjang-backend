package com.coniverse.dangjang.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 가짜 bean을 등록하기 위한 custom annotation
 *
 * @author TEO
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Primary
@Component
public @interface FakeBean {
}
