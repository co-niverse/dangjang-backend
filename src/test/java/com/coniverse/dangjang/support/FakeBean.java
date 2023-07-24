package com.coniverse.dangjang.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Primary
@Component
public @interface FakeBean {
}
