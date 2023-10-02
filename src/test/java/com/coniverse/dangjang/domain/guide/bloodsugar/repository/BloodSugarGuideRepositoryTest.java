package com.coniverse.dangjang.domain.guide.bloodsugar.repository;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.support.annotation.MongoRepositoryTest;

/**
 * @author TEO
 * @since 1.0.0
 */
@MongoRepositoryTest
class BloodSugarGuideRepositoryTest {
	@Autowired
	private BloodSugarGuideRepository bloodSugarGuideRepository;
	private static final String oauthId = "12341234";
	private static final String createdAt = "2023-12-31";

	@Test
	void 혈당_가이드를_성공적으로_저장한다() {
		// given & when
		BloodSugarGuide 혈당_가이드 = bloodSugarGuideRepository.save(혈당_가이드_도큐먼트(oauthId, createdAt));

		// then
		BloodSugarGuide 찾은_혈당_가이드 = bloodSugarGuideRepository.findById(혈당_가이드.getId()).orElseThrow();
		assertThat(찾은_혈당_가이드.getOauthId()).isEqualTo(oauthId);
		assertThat(찾은_혈당_가이드.getCreatedAt()).isEqualTo(createdAt);
	}
}
