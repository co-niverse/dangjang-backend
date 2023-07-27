package com.coniverse.dangjang.domain.auth.dto.Response;

/**
 * 로그인 RESPONSE
 *
 * @author EVE
 * @since 1.0
 */

public record LoginResponse(String nickname, String accessToken, String refreshToken, Boolean dangjangClub, Boolean healthConnect) {

}
