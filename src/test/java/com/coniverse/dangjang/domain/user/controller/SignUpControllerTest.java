package com.coniverse.dangjang.domain.user.controller;

import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;
import com.coniverse.dangjang.domain.user.service.UserSignupService;
import com.coniverse.dangjang.fixture.SignUpFixture;
import com.coniverse.dangjang.support.ControllerTest;

/**
 * @author EVE
 * @since 1.0.0
 */
class SignUpControllerTest extends ControllerTest {
	private final String URI = "/api/signUp";
	@Autowired
	private UserSignupService userSignupService;

	@Test
	void 회원가입_성공한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

	@Test
	void accessToken이_비어있는_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);

	}

	@Test
	void provider가_대문자인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("accessToken", "test", "KAKAO", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 생일이_현재보다_미래인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("accessToken", "test", "kakao", false, LocalDate.parse("2027-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"test90", "", "testtesttesttesttest"})
	void 조건에_맞지_않는_닉네임_회원가입_요청을_Bad_Request_반환한다(String nickname) throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", nickname, "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 성별이_null인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", null, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when
		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 몸무게가_0kg미만인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, -1, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 키가_0cm미만인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), -1, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 하루활동량이_소문자인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "low",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 당뇨여부가_비어있는_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			null, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 당뇨기간이_0년미만인_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, -1, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 약여부가_비어있는_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, null, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 주사여부가_비어있는_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("저혈당");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, null,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 질병리스트_값이_비어있는_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		List<String> diseases = new ArrayList<>();
		diseases.add("");
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			diseases);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

	@Test
	void 질병리스트가_없는_회원가입_요청을_Bad_Request_반환한다() throws Exception {
		//given 회원가입
		SignUpRequest signUpRequest = SignUpFixture.getSignUpRequest("287873365589", "test", "kakao", false, LocalDate.parse("2021-06-21"), 150, 50, "LOW",
			false, 0, false, false,
			null);
		LoginResponse loginResponse = new LoginResponse("test", "accessToken", "refreshToken", false, false);
		given(userSignupService.signUp(any())).willReturn(loginResponse);
		String content = objectMapper.writeValueAsString(signUpRequest);
		// when

		ResultActions resultActions = post(mockMvc, URI, content);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.message").value("올바르지 못한 데이터입니다.")
		);
	}

}
