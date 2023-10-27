package com.coniverse.dangjang.domain.version.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 앱 버전 post dto
 *
 * @author TEO
 * @since 1.3.0
 */
public record VersionRequest(@NotBlank @Pattern(regexp = "^\\d\\.\\d\\.\\d$") String minVersion,
							 @NotBlank @Pattern(regexp = "^\\d\\.\\d\\.\\d$") String latestVersion, @NotBlank String key) {
}
