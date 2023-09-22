package com.coniverse.dangjang.domain.point.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.point.entity.Point;
import com.coniverse.dangjang.domain.point.enums.PointType;
import com.coniverse.dangjang.domain.user.entity.User;

/**
 * Point Mapper
 *
 * @author EVE
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", imports = {PointType.class})
public interface PointMapper {
	@Mapping(target = "type", source = "pointType")
	Point toEntity(PointType pointType, User user, int balancePoint);
}
