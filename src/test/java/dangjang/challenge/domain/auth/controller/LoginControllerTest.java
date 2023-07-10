package dangjang.challenge.domain.auth.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;

import dangjang.challenge.ControllerTest;
import dangjang.challenge.domain.auth.service.AuthTokensGenerator;
import dangjang.challenge.domain.auth.service.OAuthLoginService;

/**
 * @author EVE
 * @since 1.0
 */
@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest extends ControllerTest {
	private final String URI = "/api/auth";
	@MockBean
	private OAuthLoginService oAuthLoginService;
	@MockBean
	private AuthTokensGenerator authTokensGenerator;

	@Test
	@WithAnonymousUser
	void 카카오_로그인_성공() throws Exception {

		// when
		ResultActions resultActions = post(URI + "/kakao",
			"{\"accessToken\" : \"accessToken123456789\"}");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);

	}

	@Test
	@WithAnonymousUser
	void 네이버_로그인_성공() throws Exception {

		// when
		ResultActions resultActions = post(URI + "/naver",
			"{\"accessToken\" : \"accessToken123456789\"}");

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())
		);
	}

}
