package com.coniverse.dangjang.domain.notification.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.notification.dto.fluentd.FcmMessage;
import com.coniverse.dangjang.domain.notification.dto.request.CheckNotificationIdRequest;
import com.coniverse.dangjang.domain.notification.dto.response.NotificationResponse;
import com.coniverse.dangjang.domain.notification.entity.Notification;
import com.coniverse.dangjang.domain.notification.entity.NotificationType;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;
import com.coniverse.dangjang.domain.notification.exception.InvalidFcmTokenException;
import com.coniverse.dangjang.domain.notification.mapper.NotificationMapper;
import com.coniverse.dangjang.domain.notification.repository.NotificationRepository;
import com.coniverse.dangjang.domain.notification.repository.UserFcmTokenRepository;
import com.coniverse.dangjang.domain.user.dto.request.FcmTokenRequest;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Notification 관련 Service
 *
 * @author EVE
 * @since 1.1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
	private final UserFcmTokenRepository userFcmTokenRepository;
	private final NotificationMapper notificationMapper;
	private final UserSearchService userSearchService;
	private final NotificationRepository notificationRepository;
	private final NotificationSearchService notificationSearchService;

	/**
	 * 유저의 알림 목록을 조회한다
	 *
	 * @param oauthId 사용자 oauthId
	 * @return List<NotificationResponse> 알림 목록
	 * @since 1.1.0
	 */
	public List<NotificationResponse> getNotificationList(String oauthId) {
		return notificationRepository.findAllByOauthIdAndRead(oauthId).stream()
			.map(notificationMapper::toResponse).collect(Collectors.toList());
	}

	/**
	 * 알림 확인 여부(isRead)를 true로 변경한다
	 *
	 * @param notificationIdList 알림Id 목록
	 * @since 1.1.0
	 */
	public void updateNotificationIsRead(CheckNotificationIdRequest notificationIdList) {
		//Todo : update 로직 수정 필요
		notificationIdList.notificationIdList().forEach(notificationId -> {
			Optional<Notification> notification = notificationRepository.findById(notificationId);
			if (notification.isPresent() && !notification.get().isRead()) {
				notification.get().setRead();
				notificationRepository.save(notification.get());
			}
		});
	}

	/**
	 * 확인 안한 알림이 있는지 조회한다.
	 *
	 * @param oauthId 사용자 oauthId
	 * @return Boolean 읽지 않은 알림이 있으면 true, 없으면 false 반환
	 * @since 1.1.0
	 */
	public Boolean isExistsNotReadNotification(String oauthId) {
		return notificationRepository.isExistsNotReadNotification(oauthId);
	}

	/**
	 * fcmToken 저장
	 * <p>
	 * 기존에 있는 fcm 토큰이라면 업데이트하고, 없다면 새로 저장한다.
	 *
	 * @param request fcmToken 요청
	 * @param oauthId 사용자 아이디
	 * @since 1.1.0
	 */
	public void saveOrUpdateFcmToken(FcmTokenRequest request, String oauthId) {
		Optional<UserFcmToken> existFcmToken = userFcmTokenRepository.findUserFcmTokenByFcmId(oauthId, request.deviceId());
		if (!existFcmToken.isEmpty()) {
			updateFcmToken(existFcmToken.get(), request.fcmToken());
			return;
		}
		saveFcmToken(request, oauthId);
	}

	/**
	 * fcmToken 업데이트
	 *
	 * @param userFcmToken 기존의 userFcmToken
	 * @param newFcmToken  새로운 fcmToken
	 * @since 1.3.0
	 */
	public void updateFcmToken(UserFcmToken userFcmToken, String newFcmToken) {
		userFcmToken.setFcmToken(newFcmToken);
	}

	/**
	 * fcmToken 저장
	 *
	 * @param request 요청
	 * @param oauthId 사용자 아이디
	 * @since 1.3.0
	 */
	public void saveFcmToken(FcmTokenRequest request, String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		UserFcmToken userFcmToken = notificationMapper.toEntity(user, request.fcmToken(), request.deviceId());
		userFcmTokenRepository.save(userFcmToken);
	}

	/**
	 * fcmToken 제거
	 *
	 * @param fcmToken fcmToken
	 * @throws InvalidFcmTokenException fcmToken이 존재하지 않을 경우 발생하는 예외
	 * @since 1.1.0
	 */
	public void deleteFcmToken(String fcmToken) {
		userFcmTokenRepository.deleteByFcmToken(fcmToken);
	}

	/**
	 * accessFcmMessage 생성
	 *
	 * @return FcmMessage
	 * @since 1.1.0
	 */
	public List<FcmMessage> makeAccessFcmMessage() {

		//Todo : accessFcmMessage 생성 로직 분리 , title,Body Enum으로 관리
		LocalDate date = LocalDate.now();
		List<UserFcmToken> userFcmTokens = notificationSearchService.findNotAccessUserFcmToken(date);
		List<String> fcmTokens = userFcmTokens.stream()
			.map(userFcmToken -> userFcmToken.getFcmToken())
			.collect(Collectors.toList());
		String title = "오늘의 접속";
		String content = "오늘 접속하지 않았어요! 접속하고 포인트를 받아가세요!";
		NotificationType notificationType = notificationSearchService.findNotificationType("접속");

		List<Notification> notifications = userFcmTokens.stream()
			.map(fcmToken -> notificationMapper.toEntity(fcmToken.getUser(), title, content, date, notificationType))
			.collect(Collectors.toList());

		notificationRepository.saveAll(notifications);

		return fcmTokens.stream()
			.map(token -> new FcmMessage(token, title, content))
			.toList();
	}
}
