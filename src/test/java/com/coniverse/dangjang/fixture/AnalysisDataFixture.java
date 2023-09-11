package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.enums.GuideString;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.global.exception.EnumNonExistentException;

/**
 * 분석 데이터 fixture
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
public class AnalysisDataFixture {
	public static AnalysisData 혈당_분석_데이터(User user, CommonCode type, String unit) {
		return new BloodSugarAnalysisData(건강지표_엔티티(user, type, unit));
	}

	public static AnalysisData 운동_분석_데이터(User user, CommonCode type, String unit) {
		return new ExerciseAnalysisData(건강지표_엔티티(user, type, unit));
	}

	public static String 걸음수_만보대비_가이드_데이터(ExerciseGuide exerciseGuide) {
		if (exerciseGuide.getNeedStepByTTS() > 0) {
			return String.format("만보보다 %d 걸음 %s", exerciseGuide.getNeedStepByTTS(), GuideString.ENOUGH.getTTSMode());
		} else if (exerciseGuide.getNeedStepByTTS() == 0) {
			return "와우! 만보를 걸었어요";
		} else {
			return String.format("만보를 걷기 위해 %d 걸음 %s", exerciseGuide.getNeedStepByTTS(), GuideString.NEEDMORE.getTTSMode());
		}
	}

	public static String 걸음수_지난주대비_가이드_데이터(ExerciseGuide exerciseGuide) {
		if (exerciseGuide.getNeedStepByLastWeek() > 0) {
			return String.format("지난주 평균 걸음 수보다 %d 걸음 %s", exerciseGuide.getNeedStepByLastWeek(),
				GuideString.ENOUGH.getLastWeekMode());
		} else if (exerciseGuide.getNeedStepByLastWeek() == 0) {
			return "지난주와 동일하게 걸었어요~ 조금 더 걸어보는건 어때요?";
		} else {
			return String.format("지난주 평균 걸음 수보다 %d 걸음 %s", Math.abs(exerciseGuide.getNeedStepByLastWeek()),
				GuideString.NEEDMORE.getLastWeekMode());
		}
	}

	public static Stream<Arguments> 운동_시간_목록() {
		return Stream.of(arguments(CommonCode.BIKE, "60"), arguments(CommonCode.HIKING, "120"), arguments(CommonCode.HEALTH, "50"),
			arguments(CommonCode.RUN, "60"),
			arguments(CommonCode.SWIM, "60"), arguments(CommonCode.WALK, "60"));
	}

	public static Stream<Arguments> 운동_시간_수정목록() {
		return Stream.of(arguments(CommonCode.BIKE, "900"), arguments(CommonCode.HIKING, "0"), arguments(CommonCode.HEALTH, "50"),
			arguments(CommonCode.RUN, "40"),
			arguments(CommonCode.SWIM, "80"), arguments(CommonCode.WALK, "120"));
	}

	public static ExerciseCalorie 운동_칼로리_데이터(CommonCode type, int unit, int weight) {
		double percent = 0;
		switch (type) {
			case WALK:
				percent = 0.9;
				break;
			case RUN:
				percent = 2;
				break;
			case BIKE:
				percent = 2.3;
				break;
			case SWIM:
				percent = 2;
				break;
			case HIKING:
				percent = 1.5;
				break;
			case HEALTH:
				percent = 1.5;
				break;
			default:
				throw new EnumNonExistentException();
		}
		int calorie = (int)(percent * weight / 15 * unit);
		return new ExerciseCalorie(type, calorie);
	}

	public static AnalysisData 체중_분석_데이터() {
		return new WeightAnalysisData(건강지표_엔티티(CommonCode.MEASUREMENT));
	}

	public static AnalysisData 체중_분석_데이터(String unit) {
		return new WeightAnalysisData(건강지표_엔티티(CommonCode.MEASUREMENT, unit));
	}

	public static String 체중_가이드_생성(WeightAnalysisData data) {
		return String.format("%s이에요. 평균 체중에 비해 %dkg %s", data.getAlert().getTitle(), data.getWeightDiff(),
			data.getWeightDiff() > 0 ? "많아요" : "적어요");
	}

	public static AnalysisData 당화혈색소_분석_데이터() { // TODO return 수정
		return new BloodSugarAnalysisData(건강지표_엔티티(CommonCode.HBA1C));
	}

	public static AnalysisData 분석_데이터(CommonCode type) {
		return new BloodSugarAnalysisData(건강지표_엔티티(type));
	}
}
