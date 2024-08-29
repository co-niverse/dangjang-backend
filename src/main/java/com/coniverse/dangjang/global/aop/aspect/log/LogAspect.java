package com.coniverse.dangjang.global.aop.aspect.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.coniverse.dangjang.domain.log.dto.server.ServerLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * log aspect
 *
 * @author TEO
 * @since 1.0.0
 */
@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
@Profile("!test & !performance")
public class LogAspect {
	private final RestTemplate restTemplate;
	@Value("${fluentbit.server-log-url}")
	private String uri;

	/**
	 * presentation layer logging
	 *
	 * @since 1.0.0
	 */
	@Around("com.coniverse.dangjang.global.aop.aspect.Pointcuts.allController()")
	public Object doRunningTimeLogPresentationLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		return sendLog("presentation_layer", joinPoint);
	}

	/**
	 * business layer logging
	 *
	 * @since 1.0.0
	 */
	@Around("com.coniverse.dangjang.global.aop.aspect.Pointcuts.allService()")
	public Object doRunningTimeLogBusinessLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		return sendLog("business_layer", joinPoint);
	}

	/**
	 * data access layer logging
	 *
	 * @since 1.0.0
	 */
	@Around("com.coniverse.dangjang.global.aop.aspect.Pointcuts.allRepository()")
	public Object doRunningTimeLogDataAccessLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		return sendLog("data_access_layer", joinPoint);
	}

	/**
	 * additional logging with annotation
	 *
	 * @see RunTimeLogging
	 * @since 1.0.0
	 */
	@Around("@annotation(RunTimeLogging)")
	public Object doRunningTimeLogWithAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
		return sendLog("additional", joinPoint);
	}

	/**
	 * send server log to ETL
	 *
	 * @since 1.0.0
	 */
	private Object sendLog(String group, ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long end = System.currentTimeMillis();

		ServerLog serverLog = new ServerLog(group, joinPoint.getSignature(), end - start);
		try {
			restTemplate.postForEntity(uri, serverLog, String.class);
		} catch (ResourceAccessException e) {
			log.error("fluentbit is dead");
		}
		return result;
	}
}
