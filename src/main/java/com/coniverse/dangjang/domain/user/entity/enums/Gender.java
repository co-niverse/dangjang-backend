package com.coniverse.dangjang.domain.user.entity.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Gender {
	M("male", false),
	F("female", true);

	@Getter
	private final String title;
	@Getter
	private final boolean isTrue;

	public static final Map<Boolean, String> byTitle = Collections.unmodifiableMap(
		Stream.of(values()).collect(Collectors.toMap(Gender::isTrue, Gender::name)));

	public static Gender of(Boolean isTrue) {
		return Gender.valueOf(byTitle.get(isTrue));
	}
}
