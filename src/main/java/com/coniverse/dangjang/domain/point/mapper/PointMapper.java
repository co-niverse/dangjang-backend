package com.coniverse.dangjang.domain.point.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.point.entity.PointLog;
import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.entity.ProductPurchase;
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
	/**
	 * Point Log Entity 변환
	 *
	 * @since 1.0.0
	 */

	@Mapping(target = "changePoint", source = "changePoint")
	PointLog toEntity(PointProduct pointProduct, User user, int changePoint, int balancePoint);

	/**
	 * UserPoint Entity 변환
	 *
	 * @since 1.0.0
	 */
	UserPoint toEntity(String oauthId, int point);

	/**
	 * ProductPurchase Entity 변환
	 *
	 * @since 1.0.0
	 */
	ProductPurchase toEntity(User user, PointProduct pointProduct, String phone);
}
