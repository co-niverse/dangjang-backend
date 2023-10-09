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
	private static String URL = "/api/point";
	@Autowired
	private PointService pointService;
	private List<PointProduct> products = 포인트_상품_목록();

	@Test
	void 포인트_상품_목록_조회() throws Exception {
		// given
		ProductListResponse response = new ProductListResponse(1000, products);
		when(pointService.getProducts(any())).thenReturn(response);
		// when
		ResultActions resultActions = get(mockMvc, URL);
		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.balancedPoint").value(response.balancedPoint()),
			jsonPath("$.data.productList").isNotEmpty()
		);
	}

	@Test
	void 포인트_상품_구매한다() throws Exception {
		// given
		UsePointRequest request = new UsePointRequest("010-xxxx-xxxx", "CU오천원금액권");
		UsePointResponse response = new UsePointResponse("010-xxxx-xxxx", "CU오천원금액권", 5000, 1000);
		when(pointService.purchaseProduct(any(), any())).thenReturn(response);
		String content = objectMapper.writeValueAsString(request);

		// when
		ResultActions resultActions = post(mockMvc, URL, content);
		// then
		resultActions.andExpectAll(
			status().isOk(),
			jsonPath("$.message").value("OK"),
			jsonPath("$.data.phone").value(request.phone()),
			jsonPath("$.data.type").value(request.type())
		);
	}
}
