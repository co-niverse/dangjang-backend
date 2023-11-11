package com.coniverse.dangjang.domain.healthmetric.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectRegisterRequest;
import com.coniverse.dangjang.domain.healthmetric.service.HealthConnectService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import given.apiversion.core.annotation.ApiVersion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * health connect controller
 *
 * @author TEO
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/health-connect")
public class HealthConnectController {
	private final HealthConnectService healthConnectService;

	/**
	 * health connect로 받은 건강 지표 데이터를 등록한다.
	 *
	 * @param requests  건강 지표 request post dto n개
	 * @param principal 유저 정보
	 * @since 1.0.0
	 * @deprecated 1.4.0
	 */
	@Deprecated(since = "1.4.0")
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<?>> postHealthConnect(@Valid @RequestBody HealthConnectPostRequest requests,
		@AuthenticationPrincipal User principal) {
		String oauthId = principal.getUsername();
		healthConnectService.registerHealthConnectData(requests, oauthId);
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * health connect로 받은 건강 지표 데이터를 등록한다.
	 *
	 * @param requests  건강 지표 request post dto n개
	 * @param principal 유저 정보
	 * @since 1.4.0
	 */
	@ApiVersion("1")
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<?>> postHealthConnectV1(@Valid @RequestBody HealthConnectPostRequest requests,
		@AuthenticationPrincipal User principal) {
		String oauthId = principal.getUsername();
		healthConnectService.registerHealthConnectData(requests, oauthId);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * health connect 연동 여부를 확인한다.
	 *
	 * @param requests  health connect 연동 여부 데이터
	 * @param principal 유저 정보
	 * @since 1.0.0
	 * @deprecated 1.4.0
	 */
	@Deprecated(since = "1.4.0")
	@PatchMapping("/interlock")
	public ResponseEntity<SuccessSingleResponse<?>> interlockHealthConnect(@Valid @RequestBody HealthConnectRegisterRequest requests,
		@AuthenticationPrincipal User principal) {
		String oauthId = principal.getUsername();
		healthConnectService.interlockHealthConnect(requests, oauthId);
		return ResponseEntity.ok(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}

	/**
	 * health connect 연동 여부를 확인한다.
	 *
	 * @param requests  health connect 연동 여부 데이터
	 * @param principal 유저 정보
	 * @since 1.4.0
	 */
	@ApiVersion("1")
	@PatchMapping("/interlock")
	public ResponseEntity<SuccessSingleResponse<?>> interlockHealthConnectV1(@Valid @RequestBody HealthConnectRegisterRequest requests,
		@AuthenticationPrincipal User principal) {
		String oauthId = principal.getUsername();
		healthConnectService.interlockHealthConnect(requests, oauthId);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), null));
	}
}
