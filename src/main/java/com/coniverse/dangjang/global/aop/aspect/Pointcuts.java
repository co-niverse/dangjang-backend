package com.coniverse.dangjang.global.aop.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * pointcut을 모아놓은 클래스
 * <p>
 * 클래스명은 @Pointcut이 존재하므로 Pointcuts로 지정
 *
 * @author TEO
 * @since 1.0.0
 */
public class Pointcuts {
	@Pointcut("allService() || allController() || allRepository()")
	public void all() {
	}

	/**
	 * *Service의 모든 메소드
	 *
	 * @since 1.0.0
	 */
	@Pointcut("execution(* com.coniverse.dangjang.domain..*Service.*(..)) || allStrategy()")
	public void allService() {
	}

	/**
	 * *Controller의 모든 메소드
	 *
	 * @since 1.0.0
	 */
	@Pointcut("execution(* com.coniverse.dangjang.domain..*Controller.*(..))")
	public void allController() {
	}

	/**
	 * *Repository의 모든 메소드
	 *
	 * @since 1.0.0
	 */
	@Pointcut("execution(* com.coniverse.dangjang.domain..*Repository.*(..))")
	public void allRepository() {
	}

	/**
	 * *Strategy의 모든 메소드
	 *
	 * @since 1.0.0
	 */
	@Pointcut("execution(* com.coniverse.dangjang.domain..*Strategy.*(..))")
	public void allStrategy() {
	}
}
