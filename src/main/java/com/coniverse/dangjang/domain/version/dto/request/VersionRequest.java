package com.coniverse.dangjang.domain.version.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 앱 버전 post dto
 *
 * @author TEO
 * @since 1.3.0
 */
public record VersionRequest(@NotBlank String minVersion, @NotBlank String latestVersion, @NotBlank String key) {
}
