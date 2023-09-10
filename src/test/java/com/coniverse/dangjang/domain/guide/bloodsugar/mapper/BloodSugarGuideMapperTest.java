package com.coniverse.dangjang.domain.guide.bloodsugar.mapper;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.TodayGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.GuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.NormalGuideFormat;
import com.coniverse.dangjang.domain.user.entity.User;

public class BloodSugarGuideMapperTest {
	private final BloodSugarGuideMapper bloodSugarGuideMapper = new BloodSugarGuideMapperImpl();

	@Test
	void 혈당_분석_데이터를_혈당_가이드_document로_변환한다() {
		// given
		User user = 유저_테오();
		CommonCode type = CommonCode.BEFORE_BREAKFAST;
		String unit = "140";
		BloodSugarAnalysisData data = 혈당_분석_데이터(user, type, unit);

		// when
		BloodSugarGuide guide = bloodSugarGuideMapper.toDocument(data);

		// that
		assertAll(() -> assertThat(guide.getOauthId()).isEqualTo(data.getOauthId()),
			() -> assertThat(guide.getCreatedAt()).isEqualTo(data.getCreatedAt().toString()), () -> assertThat(guide.getTodayGuides()).hasSize(5),
			() -> assertThat(guide.getSubGuides()).isNotNull());
	}

	@Test
	void 혈당_가이드_document를_혈당_가이드_response로_변환한다() {
		// given
		BloodSugarGuide guide = 혈당_가이드_도큐먼트("11111111", "2023-12-31");

		// when
		BloodSugarGuideResponse response = bloodSugarGuideMapper.toResponse(guide);

		// then
		assertAll(
			() -> assertThat(response.createdAt()).isEqualTo(guide.getCreatedAt()),
			() -> assertTrue(
				IntStream.range(0, 5).allMatch(i -> {
					TodayGuide 오늘의_가이드_응답 = response.todayGuides().get(i);
					TodayGuide 저장된_오늘의_가이드 = guide.getTodayGuides().get(i);
					return 오늘의_가이드_응답.getAlert().equals(저장된_오늘의_가이드.getAlert())
						&& 오늘의_가이드_응답.getCount() == 저장된_오늘의_가이드.getCount();
				})
			),
			() -> assertTrue(
				IntStream.range(0, 5).allMatch(i -> {
					SubGuideResponse 서브_가이드_응답 = response.guides().get(i);
					SubGuide 저장된_서브_가이드 = guide.getSubGuides().get(i);
					return 서브_가이드_응답.type().equals(저장된_서브_가이드.getType().getTitle())
						&& 서브_가이드_응답.unit().equals(저장된_서브_가이드.getUnit())
						&& 서브_가이드_응답.title().equals(저장된_서브_가이드.getTitle())
						&& 서브_가이드_응답.content().equals(저장된_서브_가이드.getContent())
						&& 서브_가이드_응답.alert().equals(저장된_서브_가이드.getAlert());
				})
			)
		);
	}

	@Test
	void 혈당_분석_데이터를_혈당_서브_가이드로_변환한다() {
		// given
		Alert alert = Alert.NORMAL;
		BloodSugarAnalysisData data = 경보_주입_혈당_분석_데이터(alert);
		GuideFormat format = NormalGuideFormat.getRandomOne();

		// when
		SubGuide subGuide = bloodSugarGuideMapper.toSubGuide(data, format);

		// then
		assertAll(
			() -> assertThat(subGuide.getAlert()).isEqualTo(alert.getTitle()),
			() -> assertThat(subGuide.getType()).isEqualTo(data.getType()),
			() -> assertThat(subGuide.getUnit()).isEqualTo(String.valueOf(data.getUnit())),
			() -> assertThat(subGuide.getTitle()).isEqualTo(format.getTitle()),
			() -> assertThat(subGuide.getContent()).isEqualTo(format.getContent())
		);
	}

	@Test
	void 혈당_서브_가이드를_단위가_없는_서브_가이드_response로_변환한다() {
		// given
		BloodSugarGuide guide = 혈당_가이드_도큐먼트("11111111", "2023-12-31");
		SubGuide subGuide = guide.getSubGuides().get(0);

		// when
		SubGuideResponse response = bloodSugarGuideMapper.toSubGuideResponse(subGuide, guide.getTodayGuides());

		// then
		assertAll(
			() -> assertThat(response.type()).isEqualTo(subGuide.getType().getTitle()),
			() -> assertThat(response.unit()).isNull(),
			() -> assertThat(response.alert()).isEqualTo(subGuide.getAlert()),
			() -> assertThat(response.title()).isEqualTo(subGuide.getTitle()),
			() -> assertThat(response.content()).isEqualTo(subGuide.getContent()),
			() -> assertTrue(
				IntStream.range(0, 5).allMatch(i -> {
					TodayGuide 오늘의_가이드_응답 = response.todayGuides().get(i);
					TodayGuide 저장된_오늘의_가이드 = guide.getTodayGuides().get(i);
					return 오늘의_가이드_응답.getAlert().equals(저장된_오늘의_가이드.getAlert())
						&& 오늘의_가이드_응답.getCount() == 저장된_오늘의_가이드.getCount();
				})
			)
		);
	}

	@Test
	void 혈당_서브_가이드를_서브_가이드_response로_변환한다() {
		// given
		BloodSugarGuide guide = 혈당_가이드_도큐먼트("11111111", "2023-12-31");
		SubGuide subGuide = guide.getSubGuides().get(0);

		// when
		SubGuideResponse response = bloodSugarGuideMapper.toSubGuideResponse(subGuide);

		// then
		assertAll(
			() -> assertThat(response.type()).isEqualTo(subGuide.getType().getTitle()),
			() -> assertThat(response.unit()).isEqualTo(subGuide.getUnit()),
			() -> assertThat(response.alert()).isEqualTo(subGuide.getAlert()),
			() -> assertThat(response.title()).isEqualTo(subGuide.getTitle()),
			() -> assertThat(response.content()).isEqualTo(subGuide.getContent())
		);
	}
}
