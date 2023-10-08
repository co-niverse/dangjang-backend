package com.coniverse.dangjang.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.user.entity.User;
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
	 * 회원 탈퇴 시 사용자를 비활성화한다.
	 *
	 * @param oauthId 회원탈퇴할 사용자의 oauthId
	 * @since 1.1.0
	 */
	public void withdraw(String oauthId) {
		User user = userSearchService.findUserByOauthId(oauthId);
		user.inactivate();
		// userRepository.save(user);
	}

	// TODO Spring Batch, 30일 뒤 삭제 시 사용자 연관관계 전부 제거
}
