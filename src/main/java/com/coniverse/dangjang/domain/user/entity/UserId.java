package com.coniverse.dangjang.domain.user.entity;

import java.io.Serializable;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class UserId implements Serializable {
	private String oauthId;
	@Enumerated(EnumType.STRING)
	private OauthProvider oauthProvider;

	// @Override
	// public boolean equals(Object obj) {
	// 	if (obj instanceof UserId) {
	// 		return this.oauthId.equals(((UserId)obj).oauthId) && this.oauthProvider.equals(((UserId)obj).oauthProvider);
	// 	}
	// 	return false;
	// }
}
