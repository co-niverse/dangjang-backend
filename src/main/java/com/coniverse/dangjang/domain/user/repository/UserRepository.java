package com.coniverse.dangjang.domain.user.repository;

import java.util.Optional;

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
	Optional<User> findByNickname(String nickname);

	Optional<User> findByOauthId(String oauthId);
}
