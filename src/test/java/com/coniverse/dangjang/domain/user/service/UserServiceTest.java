package com.coniverse.dangjang.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.DuplicateNicknameResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.fixture.SignUpFixture;

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
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);

		//when
		LoginResponse loginResponse = userService.signUp(signUpRequest);
		//that
		assertThat(loginResponse.accessToken()).isNotNull();
		assertThat(loginResponse.refreshToken()).isNotNull();
		assertThat(loginResponse.nickname()).isEqualTo(signUpRequest.nickname());
		assertThat(loginResponse.dangjangClub()).isEqualTo(false);
		assertThat(loginResponse.healthConnect()).isEqualTo(false);
	}

	@Test()
	void 새로운_유저를_추가한다_네이버() {
		//given
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "naver", true, LocalDate.parse("2021-06-21"), 150, 50, "MEDIUM",
			false, 0, false, false,
			diseases);

		//when
		LoginResponse loginResponse = userService.signUp(signUpRequest);
		//that
		assertThat(loginResponse.accessToken()).isNotNull();
		assertThat(loginResponse.refreshToken()).isNotNull();
		assertThat(loginResponse.nickname()).isEqualTo(signUpRequest.nickname());
		assertThat(loginResponse.dangjangClub()).isEqualTo(false);
		assertThat(loginResponse.healthConnect()).isEqualTo(false);
	}

	@Test()
	void 중복된_닉네임을_확인한다() {
		//given
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "naver", true, LocalDate.parse("2021-06-21"), 150, 50, "MEDIUM",
			false, 0, false, false,
			diseases);

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
