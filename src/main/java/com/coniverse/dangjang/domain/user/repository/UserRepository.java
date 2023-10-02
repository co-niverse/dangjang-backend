package com.coniverse.dangjang.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
