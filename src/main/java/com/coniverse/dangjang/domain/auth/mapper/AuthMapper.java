package com.coniverse.dangjang.domain.auth.mapper;

import org.mapstruct.Mapper;

import com.coniverse.dangjang.domain.auth.dto.response.LoginResponse;

/**
 * Auth Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface AuthMapper {
	LoginResponse toLoginResponse(String nickname, boolean dangjangClub, boolean healthConnect);
}
