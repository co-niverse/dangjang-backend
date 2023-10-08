package com.coniverse.dangjang.domain.user.service;

import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.Status;
import com.coniverse.dangjang.domain.user.exception.WithdrawalUserException;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author TEO
 * @since 1.1.0
 */
@SpringBootTest
class UserWithdrawalServiceTest {
	@Autowired
	private UserWithdrawalService userWithdrawalService;
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

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
}