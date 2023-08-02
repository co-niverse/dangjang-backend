package com.coniverse.dangjang.domain.user.dto;

/**
 * @author EVE
 * @since 1.0.0
 */

public record UserResponse(String oauthId, String nickname) { //ToDo: 회원가입할 때, mapper 사용하기 , record 객체로 변경 ,  그때 DTO 이름 변경 필요
}
