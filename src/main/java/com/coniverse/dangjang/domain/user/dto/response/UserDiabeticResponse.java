package com.coniverse.dangjang.domain.user.dto.response;

/**
 * 유저 당뇨 정보
 *
 * @author EVE
 * @since 1.1.0
 */
public record UserDiabeticResponse(boolean gender, int birthYear, boolean diabetic, int diabetesYear, String weightAlert) {
}