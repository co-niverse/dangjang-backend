package com.coniverse.dangjang.domain.user.dto.response;

/**
 * User log
 * <p>
 * 로깅에 필요한 유저 정보
 * 홈 API 호출 시, 로깅에 필요한 유저 정보를 전달한다
 *
 * @author EVE
 * @since 1.1.0
 */
public record UserLog(boolean gender, int birthYear, boolean diabetic, int diabetesYear, String weightAlert) {
}