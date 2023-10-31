package com.coniverse.dangjang.domain.guide.bloodsugar.document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.exception.GuideAlreadyExistsException;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 혈당 가이드 entity
 *
 * @author TEO
 * @since 1.0.0
 */
@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BloodSugarGuide {
	@Id
	private String id;
	private String oauthId;
	private String createdAt;
	private List<TodayGuide> todayGuides;
	private List<SubGuide> subGuides;

	@Builder
	private BloodSugarGuide(String oauthId, String createdAt) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.subGuides = new ArrayList<>();
		this.todayGuides = new ArrayList<>();
		todayGuides.add(new TodayGuide(Alert.HYPOGLYCEMIA.getTitle()));
		todayGuides.add(new TodayGuide(Alert.HYPOGLYCEMIA_SUSPECT.getTitle()));
		todayGuides.add(new TodayGuide(Alert.NORMAL.getTitle()));
		todayGuides.add(new TodayGuide(Alert.CAUTION.getTitle()));
		todayGuides.add(new TodayGuide(Alert.WARNING.getTitle()));
	}

	/**
	 * 서브 가이드를 추가한다.
	 *
	 * @param subGuide 서브 가이드
	 * @since 1.0.0
	 */
	public void addSubGuide(SubGuide subGuide) {
		verifySubGuideNonExistent(subGuide.getType());
		this.subGuides.add(subGuide);
		sortSubGuides();
		plusAlertCount(subGuide.getAlert());
	}

	/**
	 * 서브 가이드를 업데이트한다.
	 *
	 * @param updatedSubGuide 서브 가이드
	 * @since 1.0.0
	 */
	public void updateSubGuide(SubGuide updatedSubGuide) {
		SubGuide subGuide = getSubGuide(updatedSubGuide.getType());
		updateAlertCount(subGuide.getAlert(), updatedSubGuide.getAlert());
		subGuide.update(updatedSubGuide);
	}

	/**
	 * 타입이 변경된 서브 가이드를 업데이트한다.
	 *
	 * @param updatedSubGuide 서브 가이드
	 * @param prevType        변경할 타입
	 * @since 1.0.0
	 */
	public void updateSubGuide(SubGuide updatedSubGuide, CommonCode prevType) {
		verifySubGuideNonExistent(updatedSubGuide.getType());
		SubGuide subGuide = getSubGuide(prevType);
		updateAlertCount(subGuide.getAlert(), updatedSubGuide.getAlert());
		subGuide.update(updatedSubGuide);
		sortSubGuides();
	}

	/**
	 * 서브 가이드가 존재하는지 검증한다.
	 *
	 * @param type 서브 가이드 타입
	 * @throws GuideAlreadyExistsException 이미 해당 가이드가 존재할 경우 발생한다.
	 * @since 1.0.0
	 */
	private void verifySubGuideNonExistent(CommonCode type) {
		boolean exists = this.subGuides.stream().anyMatch(guide -> guide.isSameType(type));
		if (exists) {
			throw new GuideAlreadyExistsException();
		}
	}

	/**
	 * 서브 가이드를 가져온다.
	 *
	 * @param type 서브 가이드 타입
	 * @return 서브 가이드
	 * @throws GuideNotFoundException 해당 타입의 서브 가이드가 존재하지 않을 경우 발생한다.
	 * @since 1.0.0
	 */
	private SubGuide getSubGuide(CommonCode type) {
		return this.subGuides.stream()
			.filter(guide -> guide.isSameType(type))
			.findFirst()
			.orElseThrow(GuideNotFoundException::new);
	}

	/**
	 * 서브 가이드를 ordinal 순으로 정렬한다.
	 *
	 * @since 1.0.0
	 */
	private void sortSubGuides() {
		this.subGuides.sort(
			Comparator.comparingInt(o -> o.getType().ordinal())
		);
	}

	/**
	 * 경보가 다르면 오늘의 가이드의 경보 개수를 업데이트한다.
	 *
	 * @param prevAlert 이전 경보
	 * @param curAlert  현재 경보
	 * @since 1.0.0
	 */
	private void updateAlertCount(String prevAlert, String curAlert) {
		if (!prevAlert.equals(curAlert)) {
			this.minusAlertCount(prevAlert);
			this.plusAlertCount(curAlert);
		}
	}

	/**
	 * 오늘의 가이드의 경보 개수를 증가시킨다.
	 *
	 * @param alert 경보
	 * @since 1.0.0
	 */
	private void plusAlertCount(String alert) {
		this.todayGuides.stream()
			.filter(guide -> guide.isSameAlert(alert))
			.findFirst()
			.ifPresent(TodayGuide::plusCount);
	}

	/**
	 * 오늘의 가이드의 경보 개수를 감소시킨다.
	 *
	 * @param alert 경보
	 * @since 1.0.0
	 */
	private void minusAlertCount(String alert) {
		this.todayGuides.stream()
			.filter(guide -> guide.isSameAlert(alert))
			.findFirst()
			.ifPresent(TodayGuide::minusCount);
	}
}
