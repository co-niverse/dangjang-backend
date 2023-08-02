package com.coniverse.dangjang.domain.auth.dto.response;

public record signInResponse(String accessToken, String refreshToken, String nickname, boolean dangjangClub, boolean healthConnect) {

}
