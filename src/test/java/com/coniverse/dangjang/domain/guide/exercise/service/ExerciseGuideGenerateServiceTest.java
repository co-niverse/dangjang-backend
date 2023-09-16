package com.coniverse.dangjang.domain.guide.exercise.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.coniverse.dangjang.domain.analysis.strategy.ExerciseAnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.WalkGuideContent;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * @author EVE
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExerciseGuideGenerateServiceTest {
	private final String 등록_일자 = "2023-12-31";
	private final LocalDateTime 등록_일자_Date = LocalDate.parse(등록_일자).atTime(9, 0, 0);
	private final User user = 유저_이브();
	private final String 체중 = "70";
	@Autowired
	private ExerciseGuideGenerateService exerciseGuideGenerateService;
	@Autowired
	private ExerciseGuideRepository exerciseGuideRepository;
	@Autowired
	private ExerciseAnalysisStrategy exerciseAnalysisStrategy;
	@MockBean
	private HealthMetricSearchService healthMetricSearchService;

	@BeforeEach
	void setUp() {
		doReturn(건강지표_엔티티(user, CommonCode.MEASUREMENT, LocalDate.parse(this.등록_일자), 체중))
			.when(healthMetricSearchService).findLastHealthMetricById(any(), any());
	}

	@Order(200)
	@ParameterizedTest
	@ValueSource(strings = {"0", "11000", "1506", "33000", "9000"})
	void 걸음수_가이드를_성공적으로_등록한다(String unit) {
		// given
		var data = exerciseAnalysisStrategy.analyze(걸음수_분석_데이터(user, CommonCode.STEP_COUNT, unit));
		exerciseGuideRepository.deleteAll();

		// when
		exerciseGuideGenerateService.createGuide(data);

		// then
		ExerciseGuide 등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(user.getOauthId(), 등록_일자_Date).orElseThrow();
		WalkGuideContent walkGuideContent = new WalkGuideContent(등록된_운동가이드.getNeedStepByTTS(), 등록된_운동가이드.getNeedStepByLastWeek());
		assertThat(등록된_운동가이드.getContent()).isEqualTo(walkGuideContent.getGuideTTS());
		assertThat(등록된_운동가이드.getComparedToLastWeek()).isEqualTo(walkGuideContent.getGuideLastWeek());
	}

	@Order(250)
	@ParameterizedTest
	@ValueSource(strings = {"10000", "0", "3000", "200", "11000"})
	void 걸음수_가이드를_성공적으로_수정한다(String unit) {
		// given
		var data = exerciseAnalysisStrategy.analyze(걸음수_분석_데이터(user, CommonCode.STEP_COUNT, unit));

		// when
		exerciseGuideGenerateService.updateGuide(data);

		// then
		ExerciseGuide 등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(user.getOauthId(), 등록_일자_Date).orElseThrow();
		WalkGuideContent walkGuideContent = new WalkGuideContent(등록된_운동가이드.getNeedStepByTTS(), 등록된_운동가이드.getNeedStepByLastWeek());
		assertThat(등록된_운동가이드.getContent()).isEqualTo(walkGuideContent.getGuideTTS());
		assertThat(등록된_운동가이드.getComparedToLastWeek()).isEqualTo(walkGuideContent.getGuideLastWeek());
	}

	private Stream<Arguments> 운동_시간_목록() {
		return Stream.of(
			Arguments.of(CommonCode.BIKE, "2023-12-01", "60"),
			Arguments.of(CommonCode.HIKING, "2023-12-01", "60"),
			Arguments.of(CommonCode.STEP_COUNT, "2023-12-01", "6000"),
			Arguments.of(CommonCode.STEP_COUNT, "2023-12-02", "6000"),
			Arguments.of(CommonCode.HEALTH, "2023-12-02", "60"),
			Arguments.of(CommonCode.RUN, "2023-12-02", "60"),
			Arguments.of(CommonCode.SWIM, "2023-12-02", "60"),
			Arguments.of(CommonCode.WALK, "2023-12-02", "60")
		);
	}

	@Order(400)
	@ParameterizedTest
	@MethodSource("운동_시간_목록")
	void 운동_가이드_성공적으로_등록한다(CommonCode type, String createdAt, String unit) {
		// given
		var data = exerciseAnalysisStrategy.analyze(운동_분석_데이터(user, type, createdAt, unit));
		Optional<ExerciseGuide> 이전_등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(user.getOauthId(), LocalDate.parse(createdAt).atTime(9, 0, 0));
		List<ExerciseCalorie> exerciseCalories = new ArrayList<>();
		int 등록되어야할_칼로리수 = 0;
		if (이전_등록된_운동가이드.isPresent()) {
			exerciseCalories = 이전_등록된_운동가이드.get().getExerciseCalories();
			등록되어야할_칼로리수 = 이전_등록된_운동가이드.get().getExerciseCalories().size();
		}
		if (!type.equals(CommonCode.STEP_COUNT)) {
			exerciseCalories.add(운동_칼로리(type, Integer.parseInt(unit), Integer.parseInt(체중)));
			등록되어야할_칼로리수 += 1;
		}

		//when
		exerciseGuideGenerateService.createGuide(data);

		// then
		ExerciseGuide 등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(user.getOauthId(), LocalDate.parse(createdAt).atTime(9, 0, 0, 0))
			.orElseThrow();

		assertThat(등록된_운동가이드.getExerciseCalories()).hasSize(등록되어야할_칼로리수);
		assertThat(등록된_운동가이드.getExerciseCalories()).isEqualTo(exerciseCalories);
	}

	private Stream<Arguments> 운동_시간_수정목록() {
		return Stream.of(
			Arguments.of(CommonCode.BIKE, "2023-12-01", "80"),
			Arguments.of(CommonCode.HIKING, "2023-12-01", "69"),
			Arguments.of(CommonCode.STEP_COUNT, "2023-12-01", "8000"),
			Arguments.of(CommonCode.STEP_COUNT, "2023-12-02", "10000"),
			Arguments.of(CommonCode.HEALTH, "2023-12-02", "40"),
			Arguments.of(CommonCode.RUN, "2023-12-02", "20"),
			Arguments.of(CommonCode.SWIM, "2023-12-02", "10"),
			Arguments.of(CommonCode.WALK, "2023-12-02", "70"));
	}

	@Order(450)
	@ParameterizedTest
	@MethodSource("운동_시간_수정목록")
	void 운동_가이드_성공적으로_수정한다(CommonCode type, String createdAt, String unit) {
		// given
		int weight = Integer.parseInt(healthMetricSearchService.findLastHealthMetricById(user.getOauthId(), CommonCode.MEASUREMENT).getUnit());
		var data = exerciseAnalysisStrategy.analyze(운동_분석_데이터(user, type, createdAt, unit));
		Optional<ExerciseGuide> 이전_등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(user.getOauthId(), LocalDate.parse(createdAt).atTime(9, 0, 0));
		int 등록되어있는_운동칼로리수 = 0;

		//when
		exerciseGuideGenerateService.updateGuide(data);

		// then
		Optional<ExerciseGuide> 등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(user.getOauthId(), LocalDate.parse(createdAt).atTime(9, 0, 0));
		if (이전_등록된_운동가이드.isPresent()) {
			등록되어있는_운동칼로리수 = 이전_등록된_운동가이드.get().getExerciseCalories().size();

		}
		assertThat(등록된_운동가이드.get().getExerciseCalories()).hasSize(등록되어있는_운동칼로리수);
		for (int i = 0; i < 등록된_운동가이드.get().getExerciseCalories().size(); i++) {
			if (등록된_운동가이드.get().getExerciseCalories().get(i).type().equals(type)) {
				assertThat(등록된_운동가이드.get().getExerciseCalories().get(i)).isEqualTo(운동_칼로리(type, Integer.parseInt(unit), weight));
				break;
			}
		}

	}
}
