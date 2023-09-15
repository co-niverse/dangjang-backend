package com.coniverse.dangjang.fixture;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.ExerciseAnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.WeightAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.analysis.enums.ExercisePercent;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
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

	public static AnalysisData 운동_분석_데이터(User user, CommonCode type, String createdAt, String unit) {
		return new ExerciseAnalysisData(건강지표_엔티티(user, type, LocalDate.parse(createdAt), unit));
	}

	public static AnalysisData 걸음수_분석_데이터(User user, CommonCode type, String unit) {
		return new ExerciseAnalysisData(건강지표_엔티티(user, type, unit));
	}

	public static Stream<Arguments> 운동_시간_목록() {
		return Stream.of(arguments(CommonCode.BIKE, "2023-12-01", "60"), arguments(CommonCode.HIKING, "2023-12-01", "60"),
			arguments(CommonCode.STEP_COUNT, "2023-12-01", "6000"),
			arguments(CommonCode.STEP_COUNT, "2023-12-02", "6000"), arguments(CommonCode.HEALTH, "2023-12-02", "60"),
			arguments(CommonCode.RUN, "2023-12-02", "60"),
			arguments(CommonCode.SWIM, "2023-12-02", "60"), arguments(CommonCode.WALK, "2023-12-02", "60"));
	}

	public static Stream<Arguments> 운동_시간_수정목록() {
		return Stream.of(arguments(CommonCode.BIKE, "2023-12-01", "80"), arguments(CommonCode.HIKING, "2023-12-01", "69"),
			arguments(CommonCode.STEP_COUNT, "2023-12-01", "8000"),
			arguments(CommonCode.STEP_COUNT, "2023-12-02", "10000"), arguments(CommonCode.HEALTH, "2023-12-02", "40"),
			arguments(CommonCode.RUN, "2023-12-02", "20"),
			arguments(CommonCode.SWIM, "2023-12-02", "10"), arguments(CommonCode.WALK, "2023-12-02", "70"));
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
		return new ExerciseCalorie(type, calorie, unit);
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

	public static HealthMetricPostRequest 체중_요청(String unit) {
		return new HealthMetricPostRequest("체중", "2023-05-06", unit);
	}

	public static HealthMetricPostRequest 운동_요청(CommonCode type, String unit) {
		return new HealthMetricPostRequest(type.getTitle(), "2023-05-06", unit);
	}

	public static Stream<Arguments> 체중분석_입력_파라미터() {

		return Stream.of(
			Arguments.of(건강지표_엔티티(CommonCode.MEASUREMENT, "40"), Alert.LOW_WEIGHT),
			Arguments.of(건강지표_엔티티(CommonCode.MEASUREMENT, "90"), Alert.NORMAL_WEIGHT),
			Arguments.of(건강지표_엔티티(CommonCode.MEASUREMENT, "95"), Alert.OVERWEIGHT),
			Arguments.of(건강지표_엔티티(CommonCode.MEASUREMENT, "100"), Alert.LEVEL_1_OBESITY),
			Arguments.of(건강지표_엔티티(CommonCode.MEASUREMENT, "120"), Alert.LEVEL_2_OBESITY),
			Arguments.of(건강지표_엔티티(CommonCode.MEASUREMENT, "180"), Alert.LEVEL_3_OBESITY)
		);
	}

	public static Stream<Arguments> bmi_입력_파라미터() {

		return Stream.of(
			Arguments.of(14.5, Alert.LOW_WEIGHT),
			Arguments.of(20.9, Alert.NORMAL_WEIGHT),
			Arguments.of(23.7, Alert.OVERWEIGHT),
			Arguments.of(28.6, Alert.LEVEL_1_OBESITY),
			Arguments.of(32.6, Alert.LEVEL_2_OBESITY),
			Arguments.of(35.3, Alert.LEVEL_3_OBESITY)
		);
	}

	private static int calculateCalorie(CommonCode type, int unit, int weight) {
		double percent = ExercisePercent.findPercentByExercise(type);
		return (int)(percent * weight / 15 * unit);
	}

	private static Stream<Arguments> 운동분석_입력_파라미터() {

		return Stream.of(
			Arguments.of(건강지표_엔티티(CommonCode.STEP_COUNT, "10000"), 0, 10000, 0),
			Arguments.of(건강지표_엔티티(CommonCode.RUN, "100"), 0, 0, calculateCalorie(CommonCode.RUN, 100, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.BIKE, "10"), 0, 0, calculateCalorie(CommonCode.BIKE, 10, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.HIKING, "60"), 0, 0, calculateCalorie(CommonCode.HIKING, 60, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.WALK, "120"), 0, 0, calculateCalorie(CommonCode.WALK, 120, 70)),
			Arguments.of(건강지표_엔티티(CommonCode.SWIM, "120"), 0, 0, calculateCalorie(CommonCode.SWIM, 120, 70))
		);
	}

}
