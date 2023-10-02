package com.coniverse.dangjang.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.response.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.fixture.SignUpFixture;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
class UserSignupServiceTest {
	@Autowired
	private UserSignupService userSignupService;
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void 새로운_유저를_추가한다_카카오() {
		//given
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);

		//when
		LoginResponse loginResponse = userSignupService.signUp(signUpRequest);
		//that
		assertThat(loginResponse.nickname()).isEqualTo(signUpRequest.nickname());
		assertThat(loginResponse.dangjangClub()).isFalse();
		assertThat(loginResponse.healthConnect()).isFalse();
	}

	@Test
	void 새로운_유저를_추가한다_네이버() {
		//given
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "naver", true, LocalDate.parse("2021-06-21"), 150, 50, "MEDIUM",
			false, 0, false, false,
			diseases);

		//when
		LoginResponse loginResponse = userSignupService.signUp(signUpRequest);
		//that
		assertThat(loginResponse.nickname()).isEqualTo(signUpRequest.nickname());
		assertThat(loginResponse.dangjangClub()).isFalse();
		assertThat(loginResponse.healthConnect()).isFalse();
	}

	@Test
	void 올바르지_않는_Provider로_유저를_추가해_회원가입에_실패한다() {
		//given
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "nope", true, LocalDate.parse("2021-06-21"), 150, 50, "MEDIUM",
			false, 0, false, false,
			diseases);
		//when&that
		assertThatThrownBy(() -> userSignupService.signUp(signUpRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 중복된_닉네임을_확인한다() {
		//given
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "naver", true, LocalDate.parse("2021-06-21"), 150, 50, "MEDIUM",
			false, 0, false, false,
			diseases);

		userSignupService.signUp(signUpRequest);
		//when
		DuplicateNicknameResponse isDuplicated = userSignupService.checkDuplicatedNickname(signUpRequest.nickname());
		//then
		assertThat(isDuplicated.duplicate()).isFalse();
	}

	@Test
	void 중복되지_않은_닉네임을_확인한다() {
		//given
		String nickname = "nickname";
		//when
		DuplicateNicknameResponse isDuplicated = userSignupService.checkDuplicatedNickname(nickname);
		//then
		assertThat(isDuplicated.duplicate()).isTrue();
	}
}
