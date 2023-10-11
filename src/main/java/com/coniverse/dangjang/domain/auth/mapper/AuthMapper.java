package com.coniverse.dangjang.domain.auth.mapper;

import org.mapstruct.Mapper;

import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;
import com.coniverse.dangjang.domain.auth.entity.RefreshToken;

/**
 * Auth Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface AuthMapper {
	/**
	 * LoginResponse로 변환
	 *
	 * @param dangjangClub  당장인 여부
	 * @param healthConnect 헬스커넥트 연동 여부
	 * @param nickname      사용자 닉네임
	 * @since 1.0.0
	 */
	LoginResponse toLoginResponse(String nickname, boolean dangjangClub, boolean healthConnect);

	/**
	 * RefreshToken Entity로 변환
	 *
	 * @param accessToken       jwt accessToken
	 * @param refreshToken      jwt refreshToken
	 * @param rtkExpirationTime 유효기간
	 * @since 1.0.0
	 */
	RefreshToken toRefreshToken(String accessToken, String refreshToken, long rtkExpirationTime);
}
