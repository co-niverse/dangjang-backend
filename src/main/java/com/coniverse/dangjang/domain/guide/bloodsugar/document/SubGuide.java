package com.coniverse.dangjang.domain.guide.bloodsugar.document;

import com.coniverse.dangjang.domain.code.enums.CommonCode;

import lombok.Builder;
import lombok.Getter;

/**
 * 혈당 서브 가이드
 *
 * @author TEO
 * @since 1.0.0
 */
@Getter
public class SubGuide {
	private CommonCode type;
	private String unit;
	private String title;
	private String content;
	private String alert;

	@Builder
	private SubGuide(CommonCode type, String unit, String title, String content, String alert) {
		this.type = type;
		this.unit = unit;
		this.title = title;
		this.content = content;
		this.alert = alert;
	}

	/**
	 * 서브 가이드를 업데이트한다.
	 *
	 * @param subGuide 업데이트된 서브 가이드
	 * @since 1.0.0
	 */
	protected void update(SubGuide subGuide) {
		this.type = subGuide.getType();
		this.unit = subGuide.getUnit();
		this.title = subGuide.getTitle();
		this.content = subGuide.getContent();
		this.alert = subGuide.getAlert();
	}

	/**
	 * 서브 가이드의 타입이 같은지 확인한다.
	 *
	 * @param type 타입
	 * @return 타입이 같으면 true, 다르면 false
	 * @since 1.0.0
	 */
	protected boolean isSameType(CommonCode type) {
		return this.type.equals(type);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SubGuide subGuide) {
			return this.type.equals(subGuide.getType());
		}
		return false;
	}
}
