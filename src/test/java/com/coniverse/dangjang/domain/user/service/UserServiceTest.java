package com.coniverse.dangjang.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.auth.dto.Response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.SignUpRequest;
import com.coniverse.dangjang.domain.user.dto.TestDto;

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
		SignUpRequest signUpRequest = TestDto.getSignUpRequest();

		//when
		LoginResponse loginResponse = userService.signUp(signUpRequest);
		//that
		assertThat(loginResponse).isNotNull();
	}

	@Test()
	void 새로운_유저를_추가한다_네이버() {
		//given
		SignUpRequest signUpRequest = TestDto.getSignUpRequest();
		signUpRequest.setActivityAmount("MEDIUM");
		signUpRequest.setProvider("naver");
		//when
		LoginResponse loginResponse = userService.signUp(signUpRequest);
		//that
		assertThat(loginResponse).isNotNull();
	}

}
