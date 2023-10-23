package com.coniverse.dangjang.fixture;

import java.util.List;

import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.entity.UserPoint;
import com.coniverse.dangjang.domain.point.enums.PointType;

/**
 * 포인트 관련 Fixture
 *
 * @author EVE
 * @since 1.0.0
 */
public class PointFixture {
	public static UserPoint 유저_포인트_생성(String oauthId, int point) {
		return UserPoint.builder()
			.oauthId(oauthId)
			.point(point)
			.build();
	}

	public static List<PointProduct> 전체_포인트_상품_목록() {
		return List.of(
			PointProduct.builder().point(100).productName("접속").type(PointType.EARN).description("1일 1회 접속으로 100 포인트를 적립할 수 있어요. ").build(),
			PointProduct.builder().point(500).productName("등록").type(PointType.EARN).description("회원가입으로 500 포인트를 적립할 수 있어요. ").build(),
			PointProduct.builder().point(500).productName("기기연동").type(PointType.EARN).description("헬스커넥트 기기연동으로 500 포인트를 적립할 수 있어요.").build(),
			PointProduct.builder().point(5000).productName("스타벅스 오천원 금액권").type(PointType.USE).build(),
			PointProduct.builder().point(5000).productName("CU 오천원 금액권").type(PointType.USE).build(),
			PointProduct.builder().point(5000).productName("다이소 오천원 금액권").type(PointType.USE).build(),
			PointProduct.builder().point(5000).productName("네이버페이 오천원 금액권").type(PointType.USE).build());
	}

	public static List<PointProduct> 구매가능_포인트_상품_목록() {
		return 전체_포인트_상품_목록().stream()
			.filter(pointProduct -> pointProduct.getType().equals(PointType.USE))
			.toList();
	}

	public static List<String> 적립_방법_목록() {
		return 전체_포인트_상품_목록().stream()
			.filter(pointProduct -> pointProduct.getType().equals(PointType.EARN))
			.map(PointProduct::getDescription)
			.toList();
	}

}
