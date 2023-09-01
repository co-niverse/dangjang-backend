package com.coniverse.dangjang.domain.guide.common.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 가이드를 찾을 수 없을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class GuideNotFoundException extends BusinessException {
	public GuideNotFoundException() {
		super(404, "가이드를 찾을 수 없습니다.");
	}
}
