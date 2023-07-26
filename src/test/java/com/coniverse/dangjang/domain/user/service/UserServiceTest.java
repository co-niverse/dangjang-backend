package com.coniverse.dangjang.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.TestRequestMethod;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
public class UserServiceTest {
	@Autowired
	private UserService userService;

	@Test()
	void 새로운_유저를_추가한다_카카오() {
		//given
		SignUpRequest signUpRequest = TestRequestMethod.getSignUpRequestKakao();

		//when
		LoginResponse loginResponse = userService.signUp(signUpRequest);
		//that
		assertThat(loginResponse).isNotNull();
	}

	@Test()
	void 새로운_유저를_추가한다_네이버() {
		//given
		SignUpRequest signUpRequest = TestRequestMethod.getSignUpRequestNaver();

		//when
		LoginResponse loginResponse = userService.signUp(signUpRequest);
		//that
		assertThat(loginResponse).isNotNull();
	}

	@Test()
	void 중복된_닉네임을_확인한다() {
		//given
		SignUpRequest signUpRequest = TestRequestMethod.getSignUpRequestNaver();

		userService.signUp(signUpRequest);
		//when
		DuplicateNicknameResponse isDuplicated = userService.checkDublicateNickname(signUpRequest.nickname());
		//then
		assertThat(isDuplicated.duplicate()).isEqualTo(false);
	}

	@Test()
	void 중복되지_않은_닉네임을_확인한다() {
		//given
		String nickname = "nickname";
		//when
		DuplicateNicknameResponse isDuplicated = userService.checkDublicateNickname(nickname);
		//then
		assertThat(isDuplicated.duplicate()).isEqualTo(true);
	}
}
