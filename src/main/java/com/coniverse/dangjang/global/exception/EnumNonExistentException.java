package com.coniverse.dangjang.global.exception;

/**
 * 존재하지 않는 enum을 요청할 때 발생하는 예외
 *
 * @author TEO
 * @since 1.0.0
 */
public class EnumNonExistentException extends BusinessException {
	public EnumNonExistentException() {
		super(400, "존재하지 않는 값입니다.");
	}
}
