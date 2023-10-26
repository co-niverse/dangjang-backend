package com.coniverse.dangjang.domain.version.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Version {
	MINIMUM("1.0.7"),
	LATEST("1.0.7");

	@Getter
	private final String version;
}
