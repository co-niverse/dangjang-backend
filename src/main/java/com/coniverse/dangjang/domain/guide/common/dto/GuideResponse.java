package com.coniverse.dangjang.domain.guide.common.dto;

import java.time.LocalDate;

/**
 * 가이드 응답 dto
 *
 * @author TEO
 * @since 1.0.0
 */
public interface GuideResponse {
	String id();

	LocalDate createdAt();

	String content();
}
