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

import jakarta.annotation.PostConstruct;
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
@Profile({"!test", "!performance"})
@Slf4j
public class LogAspect {
	private final RestTemplate restTemplate;
	@Value("${fluentbit.host}")
	private String host;
	@Value("${fluentbit.server-port}")
	private String port;
	private String url;

	@PostConstruct
	private void setUrl() {
		this.url = "http://" + host + ":" + port + "/server.log";
	}

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
	 * log ETL send
	 *
	 * @since 1.0.0
	 */
	private Object sendLog(String group, ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long end = System.currentTimeMillis();

		String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		ServerLog serverLog = new ServerLog(group, className, methodName, end - start);
		try {
			restTemplate.postForEntity(url, serverLog, String.class);
		} catch (ResourceAccessException e) {
			log.error("fluentbit server.log port dead");
		}
		return result;
	}
}
