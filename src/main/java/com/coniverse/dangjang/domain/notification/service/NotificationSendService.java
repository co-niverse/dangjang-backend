package com.coniverse.dangjang.domain.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.infrastructure.sidecar.SideCarSender;
import com.coniverse.dangjang.domain.notification.dto.message.FcmMessage;

import lombok.RequiredArgsConstructor;

/**
 * 푸쉬 알림 전송 서비스
 *
 * @author EVE
 * @since 1.1.0
 */
@Service
@RequiredArgsConstructor
public class NotificationSendService {
	private final SideCarSender sideCarSender;
	@Value("${fluentbit.notification-url}")
	private final String uri;

	public void sendMessage(FcmMessage message) {
		sideCarSender.send(uri, message);
	}
}
