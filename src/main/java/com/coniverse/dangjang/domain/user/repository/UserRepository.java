package com.coniverse.dangjang.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coniverse.dangjang.domain.user.entity.User;

/**
 * USER Repository
 *
 * @author EVE
 * @since 1.0
 */
// public interface UserRepository extends JpaRepository<User, UserId> {
public interface UserRepository extends JpaRepository<User, String> {
	/**
	 * OauthID(카카오,네이버 사용자 ID)로 사용자 정보 조회
	 *
	 * @since 1.0
	 */
	// @Query("SELECT u FROM User u WHERE u.userId.oauthId = ?1 AND u.userId.oauthProvider = ?2")
	// Optional<User> findByUserId(String oauthId, OauthProvider oauthProvider);
}
