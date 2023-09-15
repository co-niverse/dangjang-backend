package com.coniverse.dangjang.domain.guide.weight.repository;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.support.annotation.MongoRepositoryTest;

/**
 * @author EVE
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MongoRepositoryTest
class WeightGuideRepositoryTest {
	@Autowired
	private WeightGuideRepository weightGuideRepository;
	private static final String 아이디 = "11111111";
	private static final String createdAt = "2023-12-31";
	private static WeightGuide 체중_가이드;

	@Order(100)
	@Test
	void 체중_가이드를_저장한다() {
		// when
		weightGuideRepository.deleteAll();
		체중_가이드 = weightGuideRepository.save(체중_가이드(아이디, createdAt));

		// then
		WeightGuide 찾은_체중_가이드 = weightGuideRepository.findByOauthIdAndCreatedAt(체중_가이드.getOauthId(), 체중_가이드.getCreatedAt()).orElseThrow();
		assertThat(찾은_체중_가이드.getOauthId()).isEqualTo(아이디);
		assertThat(찾은_체중_가이드.getCreatedAt()).isEqualTo(createdAt);
	}

	@Order(200)
	@Test
	void 체중_가이드를_조회한다() {
		// when

		// then
		WeightGuide 찾은_체중_가이드 = weightGuideRepository.findByOauthIdAndCreatedAt(체중_가이드.getOauthId(), 체중_가이드.getCreatedAt()).orElseThrow();
		assertThat(찾은_체중_가이드.getOauthId()).isEqualTo(아이디);
		assertThat(찾은_체중_가이드.getCreatedAt()).isEqualTo(createdAt);
	}
}
