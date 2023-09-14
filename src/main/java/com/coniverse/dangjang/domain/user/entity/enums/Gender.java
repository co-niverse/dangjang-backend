package com.coniverse.dangjang.domain.user.entity.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User 성별
 *
 * @author EVE
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum Gender {
	M("male", false, 22),
	F("female", true, 21);

	private final String title;
	private final boolean isTrue;
	private final int percent;

	public static final Map<Boolean, String> byTitle = Collections.unmodifiableMap(
		Stream.of(values()).collect(Collectors.toMap(Gender::isTrue, Gender::name)));

	public static Gender of(Boolean isTrue) {
		return Gender.valueOf(byTitle.get(isTrue));
	}

	public int getPercent() {
		return this.percent;
	}
}
