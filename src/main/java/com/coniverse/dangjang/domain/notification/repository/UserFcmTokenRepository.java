package com.coniverse.dangjang.domain.notification.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coniverse.dangjang.domain.notification.entity.FcmId;
import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;

/**
 * notification Repository
 *
 * @author EVE
 * @since 1.1.0
 */
public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, FcmId> {
	/**
	 * 접속하지 않은, 유저의 fcmToken을 조회한다.
	 *
	 * @param date 조회 날짜
	 * @since 1.1.0
	 */
	@Query("SELECT utk FROM UserFcmToken utk where utk.user.accessedAt < :date")
	List<UserFcmToken> findNotAccessUserFcmToken(@Param("date") LocalDate date);

	/**
	 * FcmId로 UserFcmToken을 조회한다
	 *
	 * @param oauthId  사용자 아이디
	 * @param deviceId 디바이스 아이디
	 * @since 1.3.0
	 */
	@Query("SELECT utk FROM UserFcmToken utk where utk.fcmId.oauthId = :oauthId and utk.fcmId.deviceId = :deviceId")
	Optional<UserFcmToken> findUserFcmTokenByFcmId(@Param("oauthId") String oauthId, @Param("deviceId") String deviceId);

	/**
	 * UserFcmToken을 제거한다
	 *
	 * @param fcmToken fcmToken
	 * @since 1.3.0
	 */
	void deleteByFcmToken(String fcmToken);
}
