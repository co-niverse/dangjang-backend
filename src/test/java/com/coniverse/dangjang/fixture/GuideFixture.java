package com.coniverse.dangjang.fixture;

import java.time.LocalDate;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;

/**
 * 가이드 fixture
 *
 * @author TEO
 * @since 1.0.0
 */
public class GuideFixture {
	public static GuideResponse 혈당_가이드_응답() {
		return new BloodSugarGuideResponse("12341234", LocalDate.now(), CommonCode.BEFORE_BREAKFAST.getTitle(), Alert.CAUTION, "가이드입니다");
	}

	public static GuideResponse 운동_가이드_응답() { // TODO return 수정
		return new BloodSugarGuideResponse("12341234", LocalDate.now(), CommonCode.STEP_COUNT.getTitle(), Alert.CAUTION, "가이드입니다");
	}

	public static GuideResponse 체중_가이드_응답() { // TODO return 수정
		return new BloodSugarGuideResponse("12341234", LocalDate.now(), CommonCode.MEASUREMENT.getTitle(), Alert.CAUTION, "가이드입니다");
	}

	public static GuideResponse 당화혈색소_가이드_응답() { // TODO return 수정
		return new BloodSugarGuideResponse("12341234", LocalDate.now(), CommonCode.HBA1C.getTitle(), Alert.CAUTION, "가이드입니다");
	}
}
