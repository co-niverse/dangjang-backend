package com.coniverse.dangjang.domain.version.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.global.exception.BadRequestException;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
class VersionServiceTest {
	@Autowired
	private VersionService versionService;

	@Nested
	class TestIntroResponse를 {
		@Test
		void 한_번_호출하면_IntroResponse가_반환된다() {
			// when
			VersionResponse<?> versionResponse = versionService.getTestIntroResponse();

			// then
			assertThat(versionResponse).isNotNull();
		}

		@Test
		void 두_번_연속_호출하면_BadRequestException이_발생한다() {
			// given
			versionService.getTestIntroResponse();

			// when & then
			assertThatThrownBy(() -> versionService.getTestIntroResponse()).isInstanceOf(BadRequestException.class);
		}
	}

	@Nested
	class ProdIntroResponse를 {
		@Test
		void 호출하면_IntroResponse가_반환된다() {
			// when
			VersionResponse<?> versionResponse = versionService.getProdIntroResponse();

			// then
			assertThat(versionResponse).isNotNull();
		}
	}
}
