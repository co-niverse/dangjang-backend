package com.coniverse.dangjang.domain.user.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender {
	M("male"),
	F("female");

	@Getter
	private final String title;
}
