package com.coniverse.dangjang.domain.healthmetric.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

public class IsNotSameGroupException extends BusinessException {
	public IsNotSameGroupException() {
		super(400, "같은 그룹의 건강지표 데이터가 아닙니다.");
	}
}
