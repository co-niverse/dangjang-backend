package dangjang.challenge.domain.intro.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import dangjang.challenge.ControllerTest;
import dangjang.challenge.domain.intro.service.IntroService;
import dangjang.challenge.global.dto.content.Content;
import dangjang.challenge.global.dto.content.SingleContent;
import dangjang.challenge.global.exception.BadRequestException;

/**
 * @author Teo
 * @since 1.0
 */
class IntroControllerTest extends ControllerTest {
	private final String URI = "/api/v1/intro";
	@MockBean
	private IntroService introService;

	@Test
	void 성공한_응답을_반환한다() throws Exception {
		// given
		Content content = new SingleContent<>(null);
		given(introService.getIntro()).willReturn(content);

		// when
		ResultActions resultActions = get(URI);

		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase())

		);
	}

	@Test
	void 실패한_응답을_반환한다() throws Exception {
		// given
		given(introService.getIntro()).willThrow(new BadRequestException());

		// when
		ResultActions resultActions = get(URI);

		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400),
			jsonPath("$.message").value("잘못된 요청입니다.")
		);
	}
}
