package com.coniverse.dangjang.domain.intro.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * splash screen time때 load할 버전 정보와 데이터를 담는 dto이다.
 *
 * @author TEO
 * @since 1.0.0
 */
public record IntroResponse<T>(String minVersion, String latestVersion, @JsonInclude(JsonInclude.Include.NON_NULL) List<T> loadData) {
}
