package com.coniverse.dangjang.domain.user.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * 탈퇴 후 30일이 지나지 않았을 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class WithdrawalUserException extends BusinessException {
	public WithdrawalUserException() {
		super(400, "탈퇴 후 30일이 지나지 않았습니다.");
	}
}
