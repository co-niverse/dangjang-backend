package com.coniverse.dangjang.domain.guide.bloodsugar.document;

import com.coniverse.dangjang.domain.analysis.enums.Alert;
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
	private String content;
	private Alert alert;

	@Builder
	private SubGuide(CommonCode type, String content, Alert alert) {
		this.type = type;
		this.content = content;
		this.alert = alert;
	}

	/**
	 * 서브 가이드의 내용, 경보를 업데이트한다.
	 *
	 * @param content 가이드 내용
	 * @param alert   경보
	 * @since 1.0.0
	 */
	protected void update(String content, Alert alert) {
		this.content = content;
		this.alert = alert;
	}

	/**
	 * 서브 가이드의 타입, 내용, 경보를 업데이트한다.
	 *
	 * @param type    타입
	 * @param content 가이드 내용
	 * @param alert   경보
	 * @since 1.0.0
	 */
	protected void update(CommonCode type, String content, Alert alert) {
		this.type = type;
		this.content = content;
		this.alert = alert;
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
}
