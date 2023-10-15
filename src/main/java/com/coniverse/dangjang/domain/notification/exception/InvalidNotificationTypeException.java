package com.coniverse.dangjang.domain.notification.exception;

import com.coniverse.dangjang.global.exception.BusinessException;

/**
 * notificationType을 찾을 수 없을 때 발생
 *
 * @author EVE
 * @since 1.1.0
 */
public class InvalidNotificationTypeException extends BusinessException {
	public InvalidNotificationTypeException() {
		super(404, "notificationType을 찾을 수 없습니다.");
	}
}
