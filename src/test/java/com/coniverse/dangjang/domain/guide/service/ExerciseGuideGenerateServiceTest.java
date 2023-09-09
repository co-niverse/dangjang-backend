package com.coniverse.dangjang.domain.guide.service;

import static com.coniverse.dangjang.fixture.AnalysisDataFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.analysis.strategy.ExerciseAnalysisStrategy;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;
import com.coniverse.dangjang.domain.guide.exercise.service.ExerciseGuideGenerateService;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthMetricPostRequest;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.healthmetric.repository.HealthMetricRepository;
import com.coniverse.dangjang.domain.healthmetric.service.HealthMetricSearchService;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExerciseGuideGenerateServiceTest {
	private final LocalDate 등록_일자 = LocalDate.of(2023, 12, 31);
	@Autowired
	private ExerciseGuideGenerateService exerciseGuideGenerateService;
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private HealthMetricSearchService healthMetricSearchService;
	@Autowired
	private ExerciseGuideRepository exerciseGuideRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExerciseAnalysisStrategy exerciseAnalysisStrategy;

	@Autowired
	private HealthMetricMapper mapper;

	private String 테오_아이디;
	private static User user;

	@BeforeAll
	void setUp() {
		user = userRepository.save(유저_테오());
		테오_아이디 = user.getOauthId();

	}

	@Order(200)
	@ParameterizedTest
	@ValueSource(strings = {"0", "11000", "1506", "33000", "9000"})
	void 걸음수_조언을_성공적으로_등록한다(String unit) {
		// given
		var data = exerciseAnalysisStrategy.analyze(운동_분석_데이터(user, CommonCode.STEP_COUNT, unit));
		exerciseGuideRepository.deleteAll();
		// when
		exerciseGuideGenerateService.createGuide(data);
		// then
		Optional<ExerciseGuide> 등록된_건강지표 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		assertThat(등록된_건강지표.get().getTodayContent()).isEqualTo(
			걸음수_만보대비_가이드_데이터(등록된_건강지표.get()));
		assertThat(등록된_건강지표.get().getComparedToLastWeek()).isEqualTo(
			걸음수_지난주대비_가이드_데이터(등록된_건강지표.get()));
	}

	@Order(250)
	@ParameterizedTest
	@ValueSource(strings = {"10000", "0", "3000", "200", "11000"})
	void 걸음수_조언을_성공적으로_수정한다(String unit) {
		// given
		var data = exerciseAnalysisStrategy.analyze(운동_분석_데이터(user, CommonCode.STEP_COUNT, unit));

		// when
		exerciseGuideGenerateService.updateGuide(data);
		// then
		Optional<ExerciseGuide> 등록된_건강지표 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		assertThat(등록된_건강지표.get().getTodayContent()).isEqualTo(걸음수_만보대비_가이드_데이터(등록된_건강지표.get()));
		assertThat(등록된_건강지표.get().getComparedToLastWeek()).isEqualTo(걸음수_지난주대비_가이드_데이터(등록된_건강지표.get()));
	}

	@Order(300)
	@Test
	void 체중_등록한다() {
		userRepository.deleteAll();
		HealthMetricPostRequest request = new HealthMetricPostRequest(CommonCode.MEASUREMENT.getTitle(), "2024-01-01", "60");
		healthMetricRepository.save(mapper.toEntity(request, user));
	}

	@Order(400)
	@ParameterizedTest
	@MethodSource("com.coniverse.dangjang.fixture.AnalysisDataFixture#운동_시간_목록")
	void 운동별_조언을_성공적으로_등록한다(CommonCode type, String unit) {
		// given
		int weight = Integer.parseInt(healthMetricSearchService.findLastHealthMetricById(user.getOauthId(), CommonCode.MEASUREMENT).getUnit());
		var data = exerciseAnalysisStrategy.analyze(운동_분석_데이터(user, type, unit));
		Optional<ExerciseGuide> 이전_등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		List<ExerciseCalorie> exerciseCalories = new ArrayList<>();
		int 등록되어있는_운동칼로리수 = 0;
		//when
		exerciseGuideGenerateService.createGuide(data);

		// then
		Optional<ExerciseGuide> 등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		if (이전_등록된_운동가이드.isPresent()) {
			등록되어있는_운동칼로리수 = 이전_등록된_운동가이드.get().getExerciseCalories().size();
			exerciseCalories = 이전_등록된_운동가이드.get().getExerciseCalories();
		}
		exerciseCalories.add(운동_칼로리_데이터(type, Integer.parseInt(unit), weight));

		assertThat(등록된_운동가이드.get().getExerciseCalories()).hasSize(등록되어있는_운동칼로리수 + 1);
		assertThat(등록된_운동가이드.get().getExerciseCalories()).isEqualTo(exerciseCalories);

	}

	@Order(450)
	@ParameterizedTest
	@MethodSource("com.coniverse.dangjang.fixture.AnalysisDataFixture#운동_시간_수정목록")
	void 운동별_조언을_성공적으로_수정한다(CommonCode type, String unit) {
		// given
		int weight = Integer.parseInt(healthMetricSearchService.findLastHealthMetricById(user.getOauthId(), CommonCode.MEASUREMENT).getUnit());
		var data = exerciseAnalysisStrategy.analyze(운동_분석_데이터(user, type, unit));
		Optional<ExerciseGuide> 이전_등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		List<ExerciseCalorie> 수정된_운동칼로리 = new ArrayList<>();
		int 등록되어있는_운동칼로리수 = 0;
		//when
		exerciseGuideGenerateService.updateGuide(data);

		// then
		Optional<ExerciseGuide> 등록된_운동가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 등록_일자);
		if (이전_등록된_운동가이드.isPresent()) {
			등록되어있는_운동칼로리수 = 이전_등록된_운동가이드.get().getExerciseCalories().size();
			수정된_운동칼로리 = 이전_등록된_운동가이드.get().getExerciseCalories();
		}
		assertThat(등록된_운동가이드.get().getExerciseCalories()).hasSize(등록되어있는_운동칼로리수);
		if (등록된_운동가이드.isPresent()) {
			for (int i = 0; i < 등록된_운동가이드.get().getExerciseCalories().size(); i++) {
				if (등록된_운동가이드.get().getExerciseCalories().get(i).type().equals(type)) {
					assertThat(등록된_운동가이드.get().getExerciseCalories().get(i)).isEqualTo(운동_칼로리_데이터(type, Integer.parseInt(unit), weight));
					break;
				}
			}
		}

	}
}
