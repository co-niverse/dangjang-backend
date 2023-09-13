package com.coniverse.dangjang.domain.guide.weight.service;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;
import com.coniverse.dangjang.domain.guide.weight.mapper.WeightMapper;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * @author EVE
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@WithDangjangUser
public class WeightGuideSearchServiceTest {
	@Autowired
	private WeightGuideRepository weightGuideRepository;
	@Autowired
	private WeightGuideSearchService weightGuideSearchService;
	@Autowired
	private WeightMapper weightMapper;
	private String 테오_아이디 = 유저_테오().getOauthId();
	private String 생성일자 = "2023-12-31";
	private WeightGuide 저장된_체중_가이드;

	@BeforeAll
	void setUp() {
		저장된_체중_가이드 = 체중_가이드(테오_아이디, 생성일자);
		weightGuideRepository.save(저장된_체중_가이드);

	}

	@Test
	void 날짜별_채중_가이드를_조회한다() {
		// given
		WeightGuideResponse 저장된_체중_가이드_응답 = weightMapper.toResponse(저장된_체중_가이드);
		// when
		WeightGuideResponse weightGuideResponse = weightGuideSearchService.findGuide(테오_아이디, LocalDate.parse(생성일자));

		// then
		assertAll(
			() -> assertThat(weightGuideResponse.bmi()).isEqualTo(저장된_체중_가이드_응답.bmi()),
			() -> assertThat(weightGuideResponse.weightDiff()).isEqualTo(저장된_체중_가이드_응답.weightDiff()),
			() -> assertThat(weightGuideResponse.type()).isEqualTo(저장된_체중_가이드_응답.type()),
			() -> assertThat(weightGuideResponse.unit()).isEqualTo(저장된_체중_가이드_응답.unit()),
			() -> assertThat(weightGuideResponse.title()).isEqualTo(저장된_체중_가이드_응답.title()),
			() -> assertThat(weightGuideResponse.content()).isEqualTo(저장된_체중_가이드_응답.content()),
			() -> assertThat(weightGuideResponse.createdAt()).isEqualTo(저장된_체중_가이드_응답.createdAt())
		);
	}

	@Test
	void 존재하지_않는_날짜의_가이드조회를_실패한다() {
		// given

		// when

		// then
		assertThrows(GuideNotFoundException.class, () -> {
			weightGuideSearchService.findGuide(테오_아이디, LocalDate.parse("3000-12-30"));
		});
	}
}
