package com.coniverse.dangjang.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.coniverse.dangjang.domain.user.dto.UserDto;
import com.coniverse.dangjang.domain.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mappings({
		@Mapping(target = "nickname", source = "nickname"),
		@Mapping(target = "role", source = "role"),
		@Mapping(target = "oauthId", source = "oauthId")
	})
	UserDto toDto(User user);
}
