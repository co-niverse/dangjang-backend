package com.coniverse.dangjang.domain.intro.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.intro.dto.IntroResponse;
import com.coniverse.dangjang.global.exception.BadRequestException;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
class IntroServiceTest {
	@Autowired
	private IntroService introService;

	@Nested
	class TestIntroResponse를 {
		@Test
		void 한_번_호출하면_IntroResponse가_반환된다() {
			// when
			IntroResponse<?> introResponse = introService.getTestIntroResponse();

			// then
			assertThat(introResponse).isNotNull();
		}

		@Test
		void 두_번_연속_호출하면_BadRequestException이_발생한다() {
			// given
			introService.getTestIntroResponse();

			// when & then
			assertThatThrownBy(() -> introService.getTestIntroResponse()).isInstanceOf(BadRequestException.class);
		}
	}

	@Nested
	class ProdIntroResponse를 {
		@Test
		void 호출하면_IntroResponse가_반환된다() {
			// when
			IntroResponse<?> introResponse = introService.getProdIntroResponse();

			// then
			assertThat(introResponse).isNotNull();
		}
	}
}
