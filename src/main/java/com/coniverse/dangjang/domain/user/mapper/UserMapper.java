package com.coniverse.dangjang.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.entity.enums.Role;
import com.coniverse.dangjang.domain.user.entity.enums.Status;

@Mapper(componentModel = "spring", imports = {Role.class, Status.class})
public interface UserMapper {
	@Mapping(target = "oauthId", expression = "java(oAuthInfoResponse.getOauthId())")
	@Mapping(target = "oauthProvider", expression = "java(oAuthInfoResponse.getOauthProvider())")
	@Mapping(target = "role", expression = "java(Role.USER)")
	@Mapping(target = "status", expression = "java(Status.ACTIVE)")
	@Mapping(target = "accessedAt", expression = "java(java.time.LocalDate.now())")
	@Mapping(target = "gender", expression = "java(gender)")
	User toEntity(OAuthInfoResponse oAuthInfoResponse, SignUpRequest signUpRequest, ActivityAmount activityAmount, Gender gender, int recommendedCalorie);
}
