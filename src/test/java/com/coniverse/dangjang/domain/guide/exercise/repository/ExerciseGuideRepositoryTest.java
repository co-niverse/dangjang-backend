package com.coniverse.dangjang.domain.guide.exercise.repository;

import static com.coniverse.dangjang.fixture.GuideFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coniverse.dangjang.domain.guide.exercise.document.ExerciseGuide;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * @author EVE
 * @since 1.0.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExerciseGuideRepositoryTest {
	@Autowired
	private ExerciseGuideRepository exerciseGuideRepository;
	private User 테오 = 유저_테오();
	private String 테오_아이디 = 테오.getOauthId();
	private String 조회_날짜 = "2023-12-31";
	private ExerciseGuide 저장하는_운동_가이드 = 운동_가이드(테오_아이디, 조회_날짜);

	@Order(100)
	@Test
	void 운동_가이드를_성공적으로_저장한다() {
		//given
		exerciseGuideRepository.deleteAll();
		ExerciseGuide 저장된_운동가이드 = exerciseGuideRepository.save(저장하는_운동_가이드);

		//when
		ExerciseGuide 조회한_운동가이드 = exerciseGuideRepository.findById(저장된_운동가이드.getId()).get();
		//then
		assertThat(조회한_운동가이드.getId()).isEqualTo(저장된_운동가이드.getId());
	}

	@Order(200)
	@Test
	void 운동_가이드를_성공적으로_조회한다() {
		//given
		//when
		ExerciseGuide 조회하는_운동_가이드 = exerciseGuideRepository.findByOauthIdAndCreatedAt(테오_아이디, 조회_날짜).get();
		//then
		assertThat(조회하는_운동_가이드.getOauthId()).isEqualTo(테오_아이디);
		assertThat(조회하는_운동_가이드.getCreatedAt()).isEqualTo(조회_날짜);
		assertThat(조회하는_운동_가이드.getStepsCount()).isEqualTo(저장하는_운동_가이드.getStepsCount());
		assertThat(조회하는_운동_가이드.getContent()).isEqualTo(저장하는_운동_가이드.getContent());
		assertThat(조회하는_운동_가이드.getExerciseCalories()).isEqualTo(저장하는_운동_가이드.getExerciseCalories());
	}
}
