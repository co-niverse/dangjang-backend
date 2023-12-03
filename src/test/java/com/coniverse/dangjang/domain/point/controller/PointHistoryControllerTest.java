package com.coniverse.dangjang.domain.point.controller;

import static com.coniverse.dangjang.fixture.PointFixture.*;
import static com.coniverse.dangjang.support.SimpleMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.coniverse.dangjang.domain.point.dto.request.UsePointRequest;
import com.coniverse.dangjang.domain.point.dto.response.ProductListResponse;
import com.coniverse.dangjang.domain.point.dto.response.UsePointResponse;
import com.coniverse.dangjang.domain.point.entity.PointProduct;
import com.coniverse.dangjang.domain.point.service.PointService;
import com.coniverse.dangjang.support.ControllerTest;
import com.coniverse.dangjang.support.annotation.WithDangjangUser;

/**
 * 포인트 로그 Controller 테스트
 *
 * @author EVE
 * @since 1.0.0
 */
@WithDangjangUser
class PointHistoryControllerTest extends ControllerTest {
	private static final String URI = "/api/v1/point";
	@Autowired
	private PointService pointService;
	private List<PointProduct> products = 구매가능_포인트_상품_목록();
	private List<String> descriptionListToEarnPoint = 적립_방법_목록();

	@Test
	void 포인트_상품_목록_조회() throws Exception {
		// given
		ProductListResponse response = new ProductListResponse(1000, products, descriptionListToEarnPoint);
		when(pointService.getProducts(any())).thenReturn(response);
		// when
		ResultActions resultActions = get(mockMvc, URI);
		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.balancedPoint").value(response.balancedPoint()),
			jsonPath("$.data.productList").isNotEmpty(),
			jsonPath("$.data.descriptionListToEarnPoint").isNotEmpty()

		);
	}

	@Test
	void 포인트_상품_구매한다() throws Exception {
		// given
		UsePointRequest request = new UsePointRequest("010-0000-0000", "CU오천원금액권", "이름", "코멘트");
		UsePointResponse response = new UsePointResponse("010-0000-0000", "CU오천원금액권", 5000, 1000, "이름", "코멘트");
		when(pointService.purchaseProduct(any(), any())).thenReturn(response);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URI, content);
		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.phone").value(request.phone()),
			jsonPath("$.data.type").value(request.type())
		);
	}

	@Test
	void 조건에_맞지않는_request로_포인트_상품_구매를_요청하면_예외를_던진다() throws Exception {
		// given
		UsePointRequest request = new UsePointRequest("010-22-0000", "CU오천원금액권", "이름", "코멘트");
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URI, content);
		// then
		resultActions.andExpectAll(
			status().isBadRequest(),
			jsonPath("$.errorCode").value(400)
		);
	}
}
