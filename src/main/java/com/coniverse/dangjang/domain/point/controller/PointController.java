package com.coniverse.dangjang.domain.point.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.point.dto.request.UsePointRequest;
import com.coniverse.dangjang.domain.point.dto.response.ProductListResponse;
import com.coniverse.dangjang.domain.point.dto.response.UsePointResponse;
import com.coniverse.dangjang.domain.point.service.PointService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import given.apiversion.core.annotation.ApiVersion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 포인트 Controller
 *
 * @author EVE
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/point")
public class PointController {
	private final PointService pointService;

	/**
	 * 포인트 상품 구매
	 *
	 * @param request 포인트 구매하고자 하는 내역 정보
	 * @return response 사용자 구매 완료 내역
	 * @since 1.0.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<UsePointResponse>> purchaseProduct(@Valid @RequestBody UsePointRequest request,
		@AuthenticationPrincipal User user) {
		UsePointResponse response = pointService.purchaseProduct(user.getUsername(), request);
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 포인트 상품 구매
	 *
	 * @param request 포인트 구매하고자 하는 내역 정보
	 * @param user    사용자 정보
	 * @return response 사용자 구매 완료 내역
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<UsePointResponse>> purchaseProductV1(@Valid @RequestBody UsePointRequest request,
		@AuthenticationPrincipal User user) {
		UsePointResponse response = pointService.purchaseProduct(user.getUsername(), request);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 구매 가능 포인트 상품 목록 조회
	 *
	 * @return response 사용자 포인트 잔액, 구매 가능한 상품 내역
	 * @since 1.0.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<ProductListResponse>> getProductList(@AuthenticationPrincipal User user) {
		ProductListResponse response = pointService.getProducts(user.getUsername());
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 구매 가능 포인트 상품 목록 조회
	 *
	 * @param user 사용자 정보
	 * @return response 사용자 포인트 잔액, 구매 가능한 상품 내역
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<ProductListResponse>> getPurchasableProducts(@AuthenticationPrincipal User user) {
		ProductListResponse response = pointService.getProducts(user.getUsername());
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
