package com.coniverse.dangjang.domain.user.entity.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActivityAmount {
	LOW("LOW"),
	MEDIUM("MEDIUM"),
	HIGH("HIGH");

	@Getter
	private final String title;

	public static final Map<String, String> byTitle = Collections.unmodifiableMap(
		Stream.of(values()).collect(Collectors.toMap(ActivityAmount::getTitle, ActivityAmount::name)));

	public static ActivityAmount of(String title) {
		return ActivityAmount.valueOf(byTitle.get(title));
	}
}
