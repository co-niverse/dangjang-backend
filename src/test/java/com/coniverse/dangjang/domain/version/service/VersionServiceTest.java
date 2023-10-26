package com.coniverse.dangjang.domain.version.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;

/**
 * @author TEO
 * @since 1.0.0
 */
@SpringBootTest
class VersionServiceTest {
	@Autowired
	private VersionService versionService;

	@Nested
	class ProdIntroResponse를 {
		@Test
		void 호출하면_IntroResponse가_반환된다() {
			// when
			VersionResponse<?> versionResponse = versionService.getVersionResponse();

			// then
			assertThat(versionResponse).isNotNull();
		}
	}
}
