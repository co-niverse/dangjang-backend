package com.coniverse.dangjang.domain.infrastructure.sidecar;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * sidecar로 메시지를 전송하는 서비스
 *
 * @author TEO
 * @since 1.6.1
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SideCarSender {
	private final RestTemplate restTemplate;

	/**
	 * sidecar로 메시지를 전송한다.
	 *
	 * @param uri  sidecar uri
	 * @param data 전송할 데이터
	 * @since 1.6.1
	 */
	public <T> void send(String uri, T data) {
		try {
			restTemplate.postForEntity(uri, data, String.class);
		} catch (ResourceAccessException e) {
			log.error("fluentbit is dead");
		}
	}
}
