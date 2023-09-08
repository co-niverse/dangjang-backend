package com.coniverse.dangjang.domain.guide.bloodsugar.document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.guide.common.document.Guide;
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
public class BloodSugarGuide implements Guide {
	@Id
	private String id;
	private String oauthId;
	private LocalDate createdAt;
	private List<SubGuide> subGuides;
	private Alert todayAlert;
	private String todayTitle;
	private String todayContent;
	private String summary;

	@Builder
	private BloodSugarGuide(String oauthId, LocalDate createdAt, Alert todayAlert, String todayTitle, String todayContent, String summary) {
		this.oauthId = oauthId;
		this.createdAt = createdAt;
		this.subGuides = new ArrayList<>();
		this.todayAlert = todayAlert;
		this.todayTitle = todayTitle;
		this.todayContent = todayContent;
		this.summary = summary;
	}

	/**
	 * 오늘의 경보, 오늘의 가이드 내용을 업데이트한다.
	 *
	 * @param todayAlert   오늘의 경보
	 * @param todayContent 오늘의 가이드 내용
	 * @since 1.0.0
	 */
	public void updateToday(Alert todayAlert, String todayTitle, String todayContent) { // TODO 22시마다 수행
		this.todayAlert = todayAlert;
		this.todayTitle = todayTitle;
		this.todayContent = todayContent;
	}

	/**
	 * 서브 가이드를 추가한다.
	 *
	 * @param subGuide 서브 가이드
	 * @since 1.0.0
	 */
	public void add(SubGuide subGuide) {
		verifySubGuideExists(subGuide.getType());
		this.subGuides.add(subGuide);
		sortSubGuides();
	}

	/**
	 * 서브 가이드를 업데이트한다.
	 *
	 * @param subGuide 서브 가이드
	 * @since 1.0.0
	 */
	public void update(SubGuide subGuide) {
		SubGuide prevSubGuide = getSubGuide(subGuide.getType());
		prevSubGuide.update(subGuide);
	}

	/**
	 * 타입이 변경된 서브 가이드를 업데이트한다.
	 *
	 * @param subGuide 서브 가이드
	 * @param prevType 변경할 타입
	 * @since 1.0.0
	 */
	public void update(SubGuide subGuide, CommonCode prevType) {
		verifySubGuideExists(subGuide.getType());
		SubGuide prevSubGuide = getSubGuide(prevType);
		prevSubGuide.update(subGuide);
		sortSubGuides();
	}

	/**
	 * 서브 가이드가 존재하는지 검증한다.
	 *
	 * @param type 서브 가이드 타입
	 * @throws GuideAlreadyExistsException 이미 해당 가이드가 존재할 경우 발생한다.
	 * @since 1.0.0
	 */
	private void verifySubGuideExists(CommonCode type) {
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
		this.subGuides.sort(Comparator.comparingInt(o -> o.getType().ordinal()));
	}
}
