package com.coniverse.dangjang.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author EVE
 * @since 1.0
 */
@Getter
@Setter
public class UserInfo { //ToDo: 회원가입할 때, mapper 사용하기 , record 객체로 변경 ,  그때 DTO 이름 변경 필요
	private Long OauthId;
	private String nickname;

	public UserInfo(Long OauthId, String nickname) {
		this.OauthId = OauthId;
		this.nickname = nickname;
	}
}
