package com.coniverse.dangjang.domain.auth.dto;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카카오,네이버 provider enum
 *
 * @author EVE
 * @since 1.0.0
 */
@AllArgsConstructor
public enum OauthProvider {
	KAKAO("kakao"),
	NAVER("naver");
	@Getter
	private final String title;

	public static final Map<String, String> byTitle = Collections.unmodifiableMap(
		Stream.of(values()).collect(Collectors.toMap(OauthProvider::getTitle, OauthProvider::name)));

	public static OauthProvider of(String title) {
		try {
			return OauthProvider.valueOf(byTitle.get(title));
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("존재하지 않는 OauthProvider 입니다.");
		}

	}
}
