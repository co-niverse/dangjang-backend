package com.coniverse.dangjang.global.exception;

/**
 * notificationType을 찾을 수 없을 때 발생
 *
 * @author EVE
 * @since 1.0.0
 */
public class InvalidNotificationTypeException extends BusinessException {
	public InvalidNotificationTypeException() {
		super(404, "notificationType을 찾을 수 없습니다.");
	}
}
