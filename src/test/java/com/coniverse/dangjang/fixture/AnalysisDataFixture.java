package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.global.exception.EnumNonExistentException;

/**
 * 분석 데이터 fixture
 *
 * @author TEO, EVE
 * @since 1.0.0
 */
public class AnalysisDataFixture {
	public static BloodSugarAnalysisData 혈당_분석_데이터(User user, CommonCode type, String unit) {
		return new BloodSugarAnalysisData(건강지표_엔티티(user, type, unit));
	}

	public static BloodSugarAnalysisData 경보_주입_혈당_분석_데이터(Alert alert) {
		BloodSugarAnalysisData data = new BloodSugarAnalysisData(건강지표_엔티티(유저_테오()));
		data.setAlert(alert);
		return data;
	}

	public static AnalysisData 운동_분석_데이터(User user, CommonCode type, String unit) {
		return new ExerciseAnalysisData(건강지표_엔티티(user, type, unit));
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

	public static WeightAnalysisData 체중_분석_데이터() {
		return new WeightAnalysisData(건강지표_엔티티(CommonCode.MEASUREMENT));
	}

	public static WeightAnalysisData 체중_분석_데이터(String unit) {
		return new WeightAnalysisData(건강지표_엔티티(CommonCode.MEASUREMENT, unit));
	}

	public static AnalysisData 당화혈색소_분석_데이터() { // TODO return 수정
		return new BloodSugarAnalysisData(건강지표_엔티티(CommonCode.HBA1C));
	}

	public static AnalysisData 분석_데이터(CommonCode type) {
		return new BloodSugarAnalysisData(건강지표_엔티티(type));
	}
}
