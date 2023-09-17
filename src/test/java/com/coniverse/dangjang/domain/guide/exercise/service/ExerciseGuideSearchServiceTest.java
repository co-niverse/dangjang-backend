package com.coniverse.dangjang.domain.guide.exercise.service;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseGuideResponse;
import com.coniverse.dangjang.domain.guide.exercise.repository.ExerciseGuideRepository;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * 운동 가이드 컨트롤러 테스트
 *
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExerciseGuideSearchServiceTest {
	@Autowired
	private ExerciseGuideSearchService exerciseGuideSearchService;
	@Autowired
	private ExerciseGuideRepository exerciseGuideRepository;
	private User 테오 = 유저_테오();
	private String 테오_아이디 = 테오.getOauthId();

	private LocalDate 저장_날짜_Date = LocalDate.parse("2023-12-31").plusDays(1);
	private LocalDate 조회_날짜_Date = LocalDate.parse("2023-12-31");
	private String 가이드가_존재하지_않는_날짜 = "3000-12-31";
	private ExerciseGuide 저장한_운동_가이드;

	@BeforeAll
	void setup() {
		exerciseGuideRepository.deleteAll();
		저장한_운동_가이드 = exerciseGuideRepository.save(운동_가이드(테오_아이디, 저장_날짜_Date));
	}

	@Test
	void 운동_가이드를_조회하여_Document를_반환한다() {
		//given

		//when
		ExerciseGuide exerciseGuide = exerciseGuideSearchService.findByOauthIdAndCreatedAt(테오_아이디, 조회_날짜_Date);

		// then
		assertThat(exerciseGuide.getOauthId()).isEqualTo(테오_아이디);
		assertThat(exerciseGuide.getContent()).isEqualTo(저장한_운동_가이드.getContent());
		assertThat(exerciseGuide.getComparedToLastWeek()).isEqualTo(저장한_운동_가이드.getComparedToLastWeek());
		assertThat(exerciseGuide.getNeedStepByLastWeek()).isEqualTo(저장한_운동_가이드.getNeedStepByLastWeek());
		assertThat(exerciseGuide.getStepCount()).isEqualTo(저장한_운동_가이드.getStepCount());
		assertThat(exerciseGuide.getExerciseCalories()).hasSize(저장한_운동_가이드.getExerciseCalories().size());
		assertThat(exerciseGuide.getNeedStepByTTS()).isEqualTo(저장한_운동_가이드.getNeedStepByTTS());

	}

	@Test
	void 운동_가이드를_조회하여_Response를_반환한다() {
		//given
		String 조회_날짜 = 조회_날짜_Date.toString();
		//when
		ExerciseGuideResponse exerciseGuide = exerciseGuideSearchService.findGuide(테오_아이디, 조회_날짜);
		//then
		assertThat(exerciseGuide.createdAt()).isEqualTo(조회_날짜);
		assertThat(exerciseGuide.content()).isEqualTo(저장한_운동_가이드.getContent());
		assertThat(exerciseGuide.comparedToLastWeek()).isEqualTo(저장한_운동_가이드.getComparedToLastWeek());
		assertThat(exerciseGuide.needStepByLastWeek()).isEqualTo(저장한_운동_가이드.getNeedStepByLastWeek());
		assertThat(exerciseGuide.stepsCount()).isEqualTo(저장한_운동_가이드.getStepCount());
		assertThat(exerciseGuide.exerciseCalories()).hasSize(저장한_운동_가이드.getExerciseCalories().size());
		assertThat(exerciseGuide.needStepByTTS()).isEqualTo(저장한_운동_가이드.getNeedStepByTTS());

	}

	@Test
	void 유효하지_않는_날짜로_조회하여_예외를_발생시킨다() {
		//given

		//when&then
		assertThrows(GuideNotFoundException.class, () -> {
			exerciseGuideSearchService.findGuide(테오_아이디, 가이드가_존재하지_않는_날짜);
		});
	}
}
