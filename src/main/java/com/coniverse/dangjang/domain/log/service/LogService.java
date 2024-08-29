package com.coniverse.dangjang.domain.log.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.infrastructure.sidecar.SideCarSender;
import com.coniverse.dangjang.domain.log.dto.app.AppLog;
import com.coniverse.dangjang.domain.log.dto.request.LogRequest;
import com.coniverse.dangjang.domain.log.mapper.LogMapper;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 행동 로그 서비스
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class LogService {
	private final SideCarSender sideCarSender;
	private final LogMapper logMapper;
	@Value("${fluentbit.app-log-url}")
	private final String uri;

	/**
	 * send app log to ETL
	 *
	 * @since 1.0.0
	 */
	public void sendLog(LogRequest request) {
		AppLog appLog = logMapper.toAppLog(request);
		sideCarSender.send(uri, appLog);
	}
}
