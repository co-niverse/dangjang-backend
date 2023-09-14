package com.coniverse.dangjang.domain.guide.exercise.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.coniverse.dangjang.domain.guide.exercise.dto.ExerciseCalorie;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 운동 가이드 Document
 *
 * @author EVE
 * @since 1.0.0
 */
@Setter
@Getter
@Document
@NoArgsConstructor
public class ExerciseGuide {
	@Id
	private String id;
	private String oauthId;
	private int needStepByTTS;
	private int needStepByLastWeek;
	private String createdAt;
	private String content;
	private String comparedToLastWeek;
	private int stepsCount;
	private List<ExerciseCalorie> exerciseCalories = new ArrayList<>();

	@Builder
	private ExerciseGuide(String oauthId, String createdAt, int needStepByTTS, String content, String comparedToLastWeek,
		int needStepByLastWeek, List<ExerciseCalorie> exerciseCalories, int stepsCount) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.needStepByTTS = needStepByTTS;
		this.content = content;
		this.comparedToLastWeek = comparedToLastWeek;
		this.needStepByLastWeek = needStepByLastWeek;
		this.exerciseCalories = exerciseCalories;
		this.stepsCount = stepsCount;
	}

	/**
	 * 가이드 ID를 설정한다.
	 *
	 * @since 1.0.0
	 */
	public void changeAboutWalk(int needStepByTTS, int needStepByLastWeek, String comparedToLastWeek, String content) {
		this.needStepByTTS = needStepByTTS;
		this.needStepByLastWeek = needStepByLastWeek;
		this.comparedToLastWeek = comparedToLastWeek;
		this.content = content;
	}

	/**
	 * 수정된 운동 칼로리를 업데이트한다.
	 * <p>
	 * 기존에 존재하는 운동 칼로리를 삭제하고, 새로운 운동 칼로리를 추가한다.
	 *
	 * @param exerciseCalorie 운동칼로리 객체
	 * @since 1.0.0
	 */
	public void changeExerciseCalories(ExerciseCalorie exerciseCalorie) {
		for (ExerciseCalorie existExerciseCalorie : exerciseCalories) {
			if (existExerciseCalorie.type().equals(exerciseCalorie.type())) {
				exerciseCalories.remove(existExerciseCalorie);
				break;
			}
		}
		exerciseCalories.add(exerciseCalorie);
	}
}
