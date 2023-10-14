package com.coniverse.dangjang.domain.notification.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coniverse.dangjang.domain.notification.entity.UserFcmToken;

/**
 * notification Repository
 *
 * @author EVE
 * @since 1.1.0
 */
@Repository
public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, String> {
	/**
	 * 접속하지 않은, 유저의 fcmToken을 조회한다.
	 *
	 * @param date 조회 날짜
	 * @since 1.1.0
	 */
	@Query("SELECT utk FROM UserFcmToken utk where utk.user.accessedAt < :date")
	List<UserFcmToken> findNotAccessUserFcmToken(@Param("date") LocalDate date);
}
