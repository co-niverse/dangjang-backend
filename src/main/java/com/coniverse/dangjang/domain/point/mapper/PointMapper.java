package com.coniverse.dangjang.domain.point.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.point.entity.PointLog;
import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * Point Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface PointMapper {
	@Mapping(target = "changePoint", source = "changePoint")
	PointLog toEntity(PointProduct pointProduct, User user, int changePoint, int balancePoint);

	UserPoint toEntity(String oauthId, int point);
}
