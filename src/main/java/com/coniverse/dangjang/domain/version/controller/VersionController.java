package com.coniverse.dangjang.domain.version.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.service.VersionService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

/**
 * 앱 버전 관련 controller
 *
 * @author TEO
 * @since 1.3.0
 */
@RestController
@RequestMapping("/api/version")
@RequiredArgsConstructor
public class VersionController {
	private final VersionService versionService;

	/**
	 * 버전 정보를 GET 요청한다.
	 *
	 * @since 1.3.0
	 */
	@GetMapping("/intro")
	public ResponseEntity<SuccessSingleResponse<VersionResponse<?>>> getIntro() {
		VersionResponse<?> versionResponse = versionService.getVersionResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), versionResponse));
	}
}
