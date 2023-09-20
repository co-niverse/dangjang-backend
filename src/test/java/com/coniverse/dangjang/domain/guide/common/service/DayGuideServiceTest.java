package com.coniverse.dangjang.domain.guide.common.service;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.dto.DayGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;
import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;

/**
 * @author EVE
 * @since 1.0.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class DayGuideServiceTest {
	@Autowired
	private DayGuideService dayGuideService;
	@Autowired
	private ExerciseGuideRepository exerciseGuideRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WeightGuideRepository weightGuideRepository;
	@Autowired
	private BloodSugarGuideRepository bloodSugarGuideRepository;
	private User 테오;
	private String 테오_아이디;
	private String 생성_날짜 = "2023-12-31";
	private LocalDate 생성_날짜_Date = LocalDate.parse(생성_날짜).plusDays(1);
	private LocalDate 조회_날짜_Date = LocalDate.parse(생성_날짜);

	private BloodSugarGuide 혈당_가이드;
	private ExerciseGuide 운동_가이드;
	private WeightGuide 체중_가이드;

	@BeforeAll
	void setUp() {
		exerciseGuideRepository.deleteAll();
		bloodSugarGuideRepository.deleteAll();
		weightGuideRepository.deleteAll();
		테오 = userRepository.save(유저_테오());
		테오_아이디 = 테오.getOauthId();
		운동_가이드 = exerciseGuideRepository.save(운동_가이드(테오_아이디, 생성_날짜_Date));
		혈당_가이드 = bloodSugarGuideRepository.save(혈당_가이드_도큐먼트(테오_아이디, 생성_날짜));
		체중_가이드 = weightGuideRepository.save(체중_가이드(테오_아이디, 생성_날짜));
	}

	@Order(100)
	@Test
	void 하루_가이드를_성공적으로_조회한다() {
		// given
		int 운동_칼로리 = 운동_가이드.getExerciseCalories().stream().mapToInt(exerciseCalorie -> exerciseCalorie.calorie()).sum();
		// when
		DayGuideResponse 하루_가이드_응답 = dayGuideService.getDayGuide(테오_아이디, 생성_날짜);
		// then
		assertThat(하루_가이드_응답.date()).isEqualTo(조회_날짜_Date);
		assertThat(하루_가이드_응답.nickname()).isEqualTo(테오.getNickname());
		assertThat(하루_가이드_응답.weight().title()).isEqualTo(체중_가이드.getContent());
		assertThat(하루_가이드_응답.weight().bmi()).isEqualTo(체중_가이드.getBmi());
		assertThat(하루_가이드_응답.weight().unit()).isEqualTo(체중_가이드.getUnit());
		assertThat(하루_가이드_응답.bloodSugars()).hasSize(혈당_가이드.getTodayGuides().size());
		assertThat(하루_가이드_응답.exercise().calorie()).isEqualTo(운동_칼로리);
		assertThat(하루_가이드_응답.exercise().stepCount()).isEqualTo(운동_가이드.getStepCount());
	}

	@Order(200)
	@Test
	void 저장된_가이드가_없을때_에러를_반환하지_않는다() {
		// given
		exerciseGuideRepository.deleteAll();
		bloodSugarGuideRepository.deleteAll();
		weightGuideRepository.deleteAll();

		// when
		DayGuideResponse 하루_가이드_응답 = dayGuideService.getDayGuide(테오_아이디, 생성_날짜);
		// then
		assertThat(하루_가이드_응답.date()).isEqualTo(조회_날짜_Date);
		assertThat(하루_가이드_응답.nickname()).isEqualTo(테오.getNickname());
		assertThat(하루_가이드_응답.weight().title()).isBlank();
		assertThat(하루_가이드_응답.weight().bmi()).isZero();
		assertThat(하루_가이드_응답.weight().unit()).isBlank();
		assertThat(하루_가이드_응답.bloodSugars()).isEmpty();
		assertThat(하루_가이드_응답.exercise().calorie()).isZero();
		assertThat(하루_가이드_응답.exercise().stepCount()).isZero();
	}
}
