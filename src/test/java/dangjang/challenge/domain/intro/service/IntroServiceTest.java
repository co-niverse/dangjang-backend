package dangjang.challenge.domain.intro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dangjang.challenge.global.dto.content.Content;
import dangjang.challenge.global.exception.BadRequestException;

@SpringBootTest
class IntroServiceTest {
	@Autowired
	private IntroService introService;

	@Test
	void 호출을_한_번하면_Content가_반환된다() {
		// when
		Content content = introService.getIntro();

		// then
		assertThat(content).isNotNull();
	}

	@Test
	void 호출을_두_번하면_BadRequestException이_발생한다() {
		// given
		introService.getIntro();

		// when & then
		assertThatThrownBy(() -> introService.getIntro()).isInstanceOf(BadRequestException.class);
	}
}
