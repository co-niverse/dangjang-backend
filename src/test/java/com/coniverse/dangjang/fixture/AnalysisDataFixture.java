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
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
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
		UserFixture userFixture = new UserFixture();
		User user = userFixture.유저_테오();
		int 테오_키 = user.getHeight();

		return Stream.of(
			Arguments.of(체중_요청("40"), user, 표준체중과의_차이(테오_키, 40, user.getGender()), 체질량지수(테오_키, 40), 체중_비만도(테오_키, 40)),
			Arguments.of(체중_요청("50"), user, 표준체중과의_차이(테오_키, 50, user.getGender()), 체질량지수(테오_키, 50), 체중_비만도(테오_키, 50)),
			Arguments.of(체중_요청("60"), user, 표준체중과의_차이(테오_키, 60, user.getGender()), 체질량지수(테오_키, 60), 체중_비만도(테오_키, 60)),
			Arguments.of(체중_요청("70"), user, 표준체중과의_차이(테오_키, 70, user.getGender()), 체질량지수(테오_키, 70), 체중_비만도(테오_키, 70)),
			Arguments.of(체중_요청("90"), user, 표준체중과의_차이(테오_키, 90, user.getGender()), 체질량지수(테오_키, 90), 체중_비만도(테오_키, 90)),
			Arguments.of(체중_요청("100"), user, 표준체중과의_차이(테오_키, 100, user.getGender()), 체질량지수(테오_키, 100), 체중_비만도(테오_키, 100)),
			Arguments.of(체중_요청("120"), user, 표준체중과의_차이(테오_키, 120, user.getGender()), 체질량지수(테오_키, 120), 체중_비만도(테오_키, 120)),
			Arguments.of(체중_요청("180"), user, 표준체중과의_차이(테오_키, 180, user.getGender()), 체질량지수(테오_키, 180), 체중_비만도(테오_키, 180))
		);
	}

	/**
	 * 체중 분석에 필요한 메서드
	 */
	public static int 표준체중과의_차이(int height, int weight, Gender gender) {
		int standardWeight;
		if (gender.equals(Gender.M)) {
			standardWeight = (int)(Math.pow(height / 100.0, 2.0) * 22);
		} else {
			standardWeight = (int)(Math.pow(height / 100.0, 2.0) * 21);
		}
		return weight - standardWeight;
	}

	public static double 체질량지수(int height, int weight) {
		return weight / Math.pow(height / 100.0, 2.0);
	}

	public static Alert 체중_비만도(int height, int weight) {
		double bmi = weight / Math.pow(height / 100.0, 2.0);
		if (bmi < 18.5) {
			return Alert.LOW_WEIGHT;
		} else if (bmi < 22.9) {
			return Alert.NORMAL_WEIGHT;
		} else if (bmi < 24.9) {
			return Alert.OVERWEIGHT;
		} else if (bmi < 29.9) {
			return Alert.LEVEL_1_OBESITY;
		} else if (bmi < 34.9) {
			return Alert.LEVEL_2_OBESITY;
		}
		return Alert.LEVEL_3_OBESITY;
	}

	private static int calculateCalorie(CommonCode type, int unit, int weight) {
		double percent = ExercisePercent.findPercentByExercise(type);
		return (int)(percent * weight / 15 * unit);
	}

	private static Stream<Arguments> 운동분석_입력_파라미터() {
		UserFixture userFixture = new UserFixture();

		return Stream.of(
			Arguments.of(운동_요청(CommonCode.STEP_COUNT, "10000"), userFixture.유저_테오(), 0, 10000, 0),
			Arguments.of(운동_요청(CommonCode.RUN, "100"), userFixture.유저_테오(), 0, 0, calculateCalorie(CommonCode.RUN, 100, 70)),
			Arguments.of(운동_요청(CommonCode.HEALTH, "80"), userFixture.유저_테오(), 0, 0, calculateCalorie(CommonCode.HEALTH, 80, 70)),
			Arguments.of(운동_요청(CommonCode.BIKE, "10"), userFixture.유저_테오(), 0, 0, calculateCalorie(CommonCode.BIKE, 10, 70)),
			Arguments.of(운동_요청(CommonCode.HIKING, "60"), userFixture.유저_테오(), 0, 0, calculateCalorie(CommonCode.HIKING, 60, 70)),
			Arguments.of(운동_요청(CommonCode.WALK, "120"), userFixture.유저_테오(), 0, 0, calculateCalorie(CommonCode.WALK, 120, 70)),
			Arguments.of(운동_요청(CommonCode.SWIM, "120"), userFixture.유저_테오(), 0, 0, calculateCalorie(CommonCode.SWIM, 120, 70))
		);
	}

}
