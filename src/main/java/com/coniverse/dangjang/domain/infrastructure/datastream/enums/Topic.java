package com.coniverse.dangjang.domain.infrastructure.datastream.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Topic {
	CLIENT_LOG("-client-log"),
	SERVER_LOG("-server-log");

	private final String name;
}
