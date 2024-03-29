package com.coniverse.dangjang.domain.healthmetric.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * health connect 연동여부 Enum
 * <p>
 * 한번도 연동한 적 없음 /연동 중/연동 해지
 *
 * @author EVE
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum HealthConnect {
	NEVER_CONNECTED(false),
	CONNECTING(true),
	DISCONNECTED(false);

	boolean isConnecting;
}
