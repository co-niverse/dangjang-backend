package com.coniverse.dangjang.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithSecurityContext;

import com.coniverse.dangjang.domain.user.entity.enums.Role;

/**
 * {@link AuthenticationPrincipal} annotation을 사용한 controller의 test를 위한 custom annotation
 * <br/>
 * {@link WithSecurityContext} annotation을 사용하여 custom security context factory를 등록한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithDangjangUserSecurityContextFactory.class)
public @interface WithDangjangUser {
	Role role() default Role.USER;
}
