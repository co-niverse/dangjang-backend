package com.coniverse.dangjang.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.NonExistentUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * user search service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSearchService {
	private final UserRepository userRepository;

	/**
	 * PK로 유저를 조회한다.
	 *
	 * @param oauthId 유저 PK
	 * @return User 유저
	 * @throws NonExistentUserException 유저가 존재하지 않을 경우 발생하는 예외
	 * @since 1.0.0
	 */
	public User findUserByOauthId(String oauthId) {
		return userRepository.findById(oauthId).orElseThrow(NonExistentUserException::new);
	}

	/**
	 * 닉네임 조회
	 *
	 * @param nickname 사용자 닉네임
	 * @return User 사용자
	 * @since 1.0.0
	 */
	public Optional<User> findNickname(String nickname) {
		return userRepository.findByNickname(nickname);
	}
}
