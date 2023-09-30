package com.coniverse.dangjang.domain.point.entity;

import com.coniverse.dangjang.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserPoint {
	@Id
	private String oauthId;
	private int point = 0;

	@OneToOne(mappedBy = "userPoint")
	private User user;

	@Builder
	public UserPoint(String oauthId, int point) {
		this.oauthId = oauthId;
		this.point = point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
