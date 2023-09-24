package com.coniverse.dangjang.domain.point.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.point.entity.Point;
import com.coniverse.dangjang.domain.point.entity.PointProduct;
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
	Point toEntity(PointProduct pointProduct, User user, int changePoint, int balancePoint);
}
