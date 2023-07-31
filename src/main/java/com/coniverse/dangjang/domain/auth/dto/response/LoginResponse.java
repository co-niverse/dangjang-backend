package com.coniverse.dangjang.domain.auth.dto.response;

/**
 * 로그인 RESPONSE
 *
 * @author EVE, TEO
 * @since 1.0.0
 */
public record LoginResponse(String oauthId, String nickname, String accessToken, String refreshToken, Long expiresIn) {
}
