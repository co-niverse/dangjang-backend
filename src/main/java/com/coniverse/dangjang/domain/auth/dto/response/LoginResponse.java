package com.coniverse.dangjang.domain.auth.dto.response;

/**
 * 로그인 RESPONSE
 *
 * @author EVE
 * @since 1.0.0
 */
public record LoginResponse(String accessToken, String refreshToken, String nickname, boolean dangjangClub, boolean healthConnect) {
}
