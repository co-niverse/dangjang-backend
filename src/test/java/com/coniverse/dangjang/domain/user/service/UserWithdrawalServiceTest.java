package com.coniverse.dangjang.domain.user.service;

import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.Status;
import com.coniverse.dangjang.domain.user.exception.WithdrawalUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author TEO
 * @since 1.1.0
 */
@SpringBootTest
@Transactional
class UserWithdrawalServiceTest {
	@Autowired
	private UserWithdrawalService userWithdrawalService;
	@Autowired
	private UserRepository userRepository;

	@Test
	void active_유저는_회원_탈퇴에_성공한다() {
		// given
		User user = userRepository.save(유저_테오());
		String oauthId = user.getOauthId();

		// when
		userWithdrawalService.withdraw(oauthId);

		// then
		User deletedUser = userRepository.findById(oauthId).orElseThrow();
		assertAll(
			() -> assertFalse(deletedUser.isActive()),
			() -> assertThat(deletedUser.getStatus()).isEqualTo(Status.INACTIVE),
			() -> assertThat(deletedUser.getInactivatedAt()).isEqualTo(LocalDate.now())
		);
	}

	@Test
	void inactive_유저는_회원_탈퇴에_실패한다() {
		// given
		User user = userRepository.save(비활성화된_유저());
		String oauthId = user.getOauthId();

		// when & then
		assertThatThrownBy(() -> userWithdrawalService.withdraw(oauthId))
			.isInstanceOf(WithdrawalUserException.class);
	}

	@Test
	void active_유저는_활성화된_사용자_검증을_통과한다() {
		// given
		User user = 유저_테오();

		// when & then
		assertDoesNotThrow(() -> userWithdrawalService.verifyActiveUser(user));
	}

	@Test
	void inactive_유저는_활성화된_사용자_검증을_통과하지_못한다() {
		// given
		User user = 비활성화된_유저();

		// when & then
		assertThatThrownBy(() -> userWithdrawalService.verifyActiveUser(user))
			.isInstanceOf(WithdrawalUserException.class);
	}
}