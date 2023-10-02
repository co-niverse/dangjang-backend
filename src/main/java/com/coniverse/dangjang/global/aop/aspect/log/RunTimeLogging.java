package com.coniverse.dangjang.global.aop.aspect.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * run time logging annotation
 *
 * @author TEO
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RunTimeLogging {
}
