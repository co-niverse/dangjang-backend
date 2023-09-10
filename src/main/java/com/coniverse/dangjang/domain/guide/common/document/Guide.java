package com.coniverse.dangjang.domain.guide.common.document;

import java.time.LocalDate;

/**
 * 가이드 document interface
 *
 * @author TEO
 * @since 1.0.0
 */
public interface Guide {
	String getOauthId();

	LocalDate getCreatedAt();

	String getUnit();

	String getTitle();

	String getContent();
}
