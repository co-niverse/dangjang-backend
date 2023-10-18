package com.coniverse.dangjang.domain.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.coniverse.dangjang.domain.notification.dto.fluentd.FcmMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * FluentBit를 통해 Notification을 전송하는 서비스
 *
 * @author EVE
 * @since 1.1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSendService {
	private final RestTemplate restTemplate;
	@Value("${fluentbit.notification-url}")
	private String url;

	public void sendMessage(FcmMessage message) {
		try {
			restTemplate.postForEntity(url, message, String.class);
		} catch (ResourceAccessException e) {
			log.error("fluentbit is dead");
		}
	}
}
