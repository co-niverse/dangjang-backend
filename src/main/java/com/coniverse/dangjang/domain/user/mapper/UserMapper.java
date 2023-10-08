package com.coniverse.dangjang.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.healthmetric.enums.HealthConnect;
import com.coniverse.dangjang.domain.infrastructure.auth.dto.OAuthInfoResponse;
import com.coniverse.dangjang.domain.user.dto.request.SignUpRequest;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.entity.enums.ActivityAmount;
import com.coniverse.dangjang.domain.user.entity.enums.Gender;
import com.coniverse.dangjang.domain.user.entity.enums.Role;
import com.coniverse.dangjang.domain.user.entity.enums.Status;

@Mapper(componentModel = "spring", imports = {Role.class, Status.class, HealthConnect.class})
public interface UserMapper {
	@Mapping(target = "oauthId", source = "oAuthInfoResponse.oauthId")
	@Mapping(target = "oauthProvider", source = "oAuthInfoResponse.oauthProvider")
	@Mapping(target = "gender", source = "gender")
	@Mapping(target = "activityAmount", source = "activityAmount")
	@Mapping(target = "diabetic", source = "signUpRequest.diabetes")
	User toEntity(OAuthInfoResponse oAuthInfoResponse, SignUpRequest signUpRequest, ActivityAmount activityAmount, Gender gender, int recommendedCalorie);
}
