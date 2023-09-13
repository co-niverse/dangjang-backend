package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BloodSugarGuideSearchServiceTest {
	@Autowired
	private BloodSugarGuideSearchService bloodSugarGuideSearchService;
	@Autowired
	private BloodSugarGuideRepository bloodSugarGuideRepository;
	@MockBean
	private UserSearchService userSearchService;
	private final String oauthId = "11111111";
	private final String createdAt = "2023-12-31";
	private BloodSugarGuide 저장된_혈당_가이드;

	@BeforeAll
	void setUp() {
		저장된_혈당_가이드 = bloodSugarGuideRepository.save(혈당_가이드_도큐먼트(oauthId, createdAt));
	}

	@Test
	void 사용자_PK와_생성일자로_혈당_가이드를_조회한다() {
		// given & when
		BloodSugarGuide 찾은_혈당_가이드 = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(oauthId, LocalDate.parse(createdAt));

		// then
		assertAll(
			() -> assertThat(찾은_혈당_가이드.getOauthId()).isEqualTo(oauthId),
			() -> assertThat(찾은_혈당_가이드.getCreatedAt()).isEqualTo(createdAt),
			() -> assertThat(찾은_혈당_가이드.getSubGuides()).hasSameSizeAs(저장된_혈당_가이드.getSubGuides())
		);
	}

	@Test
	void 존재하지_않는_생성일자로_혈당_가이드를_조회하면_예외를_발생한다() {
		// given
		LocalDate 없는_생성일자 = LocalDate.parse("2023-12-30");

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideSearchService.findByUserIdAndCreatedAt(oauthId, 없는_생성일자))
			.isInstanceOf(GuideNotFoundException.class);
	}

	@Test
	void 사용자_PK와_찾는_일자로_혈당_가이드를_조회한다() {
		// given
		doReturn(null).when(userSearchService).findUserByOauthId(anyString());

		// when
		BloodSugarGuideResponse 찾은_혈당_가이드_응답 = bloodSugarGuideSearchService.findGuide(oauthId, createdAt);

		// then
		assertThat(찾은_혈당_가이드_응답.createdAt()).isEqualTo(createdAt);
		assertThat(찾은_혈당_가이드_응답.guides()).hasSameSizeAs(저장된_혈당_가이드.getSubGuides());
		assertThat(찾은_혈당_가이드_응답.guides()).hasSameSizeAs(저장된_혈당_가이드.getSubGuides());
	}

	@Test
	void 존재하지_않는_찾는_일자로_혈당_가이드를_조회하면_예외를_발생한다() {
		// given
		String 없는_생성일자 = "2023-12-30";

		// when & then
		assertThatThrownBy(() -> bloodSugarGuideSearchService.findGuide(oauthId, 없는_생성일자))
			.isInstanceOf(GuideNotFoundException.class);
	}
}
