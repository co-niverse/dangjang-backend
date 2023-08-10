package com.coniverse.dangjang.domain.healthmetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 같은 그룹의 건강지표 데이터가 아닐 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class IsNotSameGroupException extends BusinessException {
	public IsNotSameGroupException() {
		super(400, "같은 그룹의 건강지표 데이터가 아닙니다.");
	}
}
