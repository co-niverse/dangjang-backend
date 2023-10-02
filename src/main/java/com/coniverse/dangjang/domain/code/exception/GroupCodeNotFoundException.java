package com.coniverse.dangjang.domain.code.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 그룹 코드를 찾을 수 없을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class GroupCodeNotFoundException extends BusinessException {
	public GroupCodeNotFoundException() {
		super(404, "상위 코드 그룹을 찾을 수 없습니다.");
	}
}
