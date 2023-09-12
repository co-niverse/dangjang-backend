package com.coniverse.dangjang.domain.healthmetric.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.service.HealthConnectRegisterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * health connect controller
 *
 * @author TEO
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/health-connect")
@RequiredArgsConstructor
public class HealthConnectController {
	private final HealthConnectRegisterService healthConnectRegisterService;

	/**
	 * health connect로 받은 건강 지표 데이터를 등록한다.
	 *
	 * @param requests  건강 지표 request post dto n개
	 * @param principal 유저 정보
	 * @since 1.0.0
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void postHealthConnect(@Valid @RequestBody HealthConnectPostRequest requests, @AuthenticationPrincipal User principal) {
		String oauthId = principal.getUsername();
		healthConnectRegisterService.registerHealthConnect(requests, oauthId);
	}
}
