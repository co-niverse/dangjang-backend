package com.coniverse.dangjang.fixture;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.point.enums.PointType;

/**
 * 포인트 관련 Fixture
 *
 * @author EVE
 * @version 1.0.0
 */
public class PointFixture {
	public static final UserPoint 유저_포인트_생성(String oauthId, int point) {
		return UserPoint.builder()
			.oauthId(oauthId)
			.point(point)
			.build();
	}

	public static final List<PointProduct> 전체_포인트_상품_목록() {
		return List.of(new PointProduct("접속", 100, PointType.EARN),
			new PointProduct("등록", 500, PointType.EARN),
			new PointProduct("기기연동", 500, PointType.EARN),
			new PointProduct("스타벅스_오천원_금액권", 5000, PointType.USE),
			new PointProduct("CU_오천원_금액권", 5000, PointType.USE),
			new PointProduct("배민_일만원_금액권", 10000, PointType.USE),
			new PointProduct("네이버페이_오천원_금액권", 5000, PointType.USE));
	}

	public static final Map<String, Integer> 적립_포인트_상품_목록() {
		return 전체_포인트_상품_목록().stream()
			.filter(pointProduct -> pointProduct.getType().equals(PointType.USE))
			.collect(Collectors.toMap(PointProduct::getProduct, PointProduct::getPoint));
	}
}
