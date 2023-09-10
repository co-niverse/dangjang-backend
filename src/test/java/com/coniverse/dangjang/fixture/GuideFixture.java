package com.coniverse.dangjang.fixture;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.SubGuideResponse;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 가이드 fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class GuideFixture {
	public static BloodSugarGuide 혈당_가이드_도큐먼트(String oauthId) {
		return BloodSugarGuide.builder()
			.oauthId(oauthId)
			.createdAt(LocalDate.now())
			.alert(Alert.CAUTION)
			.content("가이드입니다")
			.summary("요약입니다.")
			.build();
	}

	public static SubGuide 혈당_서브_가이드(CommonCode type) {
		return SubGuide.builder()
			.type(type)
			.alert(Alert.CAUTION.getTitle())
			.unit("100")
			.title("가이드 제목입니다.")
			.content("가이드 내용입니다.")
			.build();
	}

	public static GuideResponse 혈당_서브_가이드_응답() {
		return new SubGuideResponse(CommonCode.BEFORE_BREAKFAST.getTitle(), null, Alert.CAUTION.getTitle(), "제목입니다", "가이드입니다");
	}

	public static GuideResponse 운동_가이드_응답() { // TODO return 수정
		return new SubGuideResponse(CommonCode.STEP_COUNT.getTitle(), null, Alert.CAUTION.getTitle(), "제목입니다.", "가이드입니다");
	}

	public static GuideResponse 체중_가이드_응답() { // TODO return 수정
		return new SubGuideResponse(CommonCode.MEASUREMENT.getTitle(), null, Alert.CAUTION.getTitle(), "제목입니다.", "가이드입니다");
	}

	public static GuideResponse 당화혈색소_가이드_응답() { // TODO return 수정
		return new SubGuideResponse(CommonCode.HBA1C.getTitle(), null, Alert.CAUTION.getTitle(), "제목입니다.", "가이드입니다");
	}
}
