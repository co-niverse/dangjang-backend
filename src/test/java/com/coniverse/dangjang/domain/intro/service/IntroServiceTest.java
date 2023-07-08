package com.coniverse.dangjang.domain.intro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.intro.dto.IntroInfo;
import com.coniverse.dangjang.global.exception.BadRequestException;

@SpringBootTest
class IntroServiceTest {
	@Autowired
	private IntroService introService;

	@Test
	void 호출을_한_번하면_IntroInfo가_반환된다() {
		// when
		IntroInfo introInfo = introService.getIntroInfoV1();

		// then
		assertThat(introInfo).isNotNull();
	}

	@Test
	void 호출을_두_번하면_BadRequestException이_발생한다() {
		// given
		introService.getIntroInfoV1();

		// when & then
		assertThatThrownBy(() -> introService.getIntroInfoV1()).isInstanceOf(BadRequestException.class);
	}
}
