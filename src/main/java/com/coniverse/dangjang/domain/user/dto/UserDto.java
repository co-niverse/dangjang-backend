package com.coniverse.dangjang.domain.user.dto;

import com.coniverse.dangjang.domain.user.entity.enums.Role;

/**
 * @author EVE
 * @since 1.0.0
 */

public record UserDto(String oauthId, String nickname, Role role) { //ToDo: 회원가입할 때, mapper 사용하기
}
