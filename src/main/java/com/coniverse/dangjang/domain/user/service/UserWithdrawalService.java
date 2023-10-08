package com.coniverse.dangjang.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.exception.WithdrawalUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 회원탈퇴 서비스
 *
 * @author TEO
 * @since 1.1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserWithdrawalService {
	private final UserRepository userRepository;
	private final UserSearchService userSearchService;

	/**
	 * 사용자를 비활성화한다. 30일 뒤 자동으로 삭제된다.
	 *
	 * @param oauthId 회원탈퇴할 사용자의 oauthId
	 * @since 1.1.0
	 */
	public void withdraw(String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		verifyActiveUser(user);
		user.inactivate();
		userRepository.save(user);
	}

	/**
	 * 사용자가 활성화된 사용자인지 확인한다.
	 *
	 * @param user 활성화 여부를 확인할 사용자
	 * @throws WithdrawalUserException 사용자가 비활성화된 사용자일 경우
	 * @since 1.1.0
	 */
	public void verifyActiveUser(User user) {
		if (!user.isActive()) {
			throw new WithdrawalUserException();
		}
	}
}
