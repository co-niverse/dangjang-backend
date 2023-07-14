package com.coniverse.dangjang.domain.user.entity;

import com.coniverse.dangjang.domain.auth.dto.OauthProvider;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Eve
 * @since 1.0
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 카카오, 네이버 사용자 ID
	 *
	 * @since 1.0
	 */
	private Long oauthId;
	private String nickname;
	private OauthProvider oAuthProvider;

	@Builder
	public User(Long oauthId, String nickname, OauthProvider oAuthProvider) {
		this.oauthId = oauthId;
		this.nickname = nickname;
		this.oAuthProvider = oAuthProvider;
	}
}
