package com.coniverse.dangjang.domain.guide.common.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 가이드가 이미 존재할 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class GuideAlreadyExistsException extends BusinessException {
	public GuideAlreadyExistsException() {
		super(400, "이미 존재하는 가이드입니다.");
	}
}
