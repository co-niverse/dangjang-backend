package com.coniverse.dangjang.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Disabled;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.coniverse.dangjang.config.TestConfig;

/**
 * performance test annotation
 *
 * @author TEO
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Disabled
@ActiveProfiles("performance")
@Import(TestConfig.class)
public @interface PerformanceTest {
}
