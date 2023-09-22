package com.coniverse.dangjang.domain.user.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.coniverse.dangjang.domain.user.entity.User;

/**
 * USER Repository
 *
 * @author EVE
 * @since 1.0.0
 */
public interface UserRepository extends JpaRepository<User, String> {
	/**
	 * nickname으로 사용자 정보 조회
	 *
	 * @since 1.0.0
	 */
	Optional<User> findByNickname(String nickname);

	/**
	 * oauthId로 사용자 정보 조회
	 *
	 * @since 1.0.0
	 */
	Optional<User> findByOauthId(String oauthId);

	/**
	 * accessed_at 갱신
	 * oauthId로 사용자 정보 조회
	 *
	 * @since 1.0.0
	 */
	@Modifying
	@Query("update users u set u.accessedAt=?2 where u.oauthId=?1")
	void updateAccessedAtByOauthId(String oauthId, LocalDate accessedAt);

	/**
	 * point 갱신
	 * oauthId로 사용자 정보 조회
	 *
	 * @since 1.0.0
	 */
	@Modifying
	@Query("update users u set u.point=?2 where u.oauthId=?1")
	void updatePointByOauthId(String oauthId, int point);
}
