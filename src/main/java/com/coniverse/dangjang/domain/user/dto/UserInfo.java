package com.coniverse.dangjang.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author EVE
 * @since 1.0
 */
@Getter
@Setter
public class UserInfo {
	private Long OauthId;
	private String nickname;

	public UserInfo(Long OauthId, String nickname) {
		this.OauthId = OauthId;
		this.nickname = nickname;
	}
}
