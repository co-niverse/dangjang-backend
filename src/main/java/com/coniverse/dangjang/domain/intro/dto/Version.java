package com.coniverse.dangjang.domain.intro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Version {
	MINIMUM("1.0.1"),
	LATEST("1.0.1");

	@Getter
	private final String version;
}
