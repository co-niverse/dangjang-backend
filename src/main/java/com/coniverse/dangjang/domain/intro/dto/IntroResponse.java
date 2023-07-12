package com.coniverse.dangjang.domain.intro.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 버전 정보, splash 정보를 담는다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record IntroResponse<T>(String minVersion, String latestVersion,
							   @JsonInclude(JsonInclude.Include.NON_NULL) List<T> home) {
}
