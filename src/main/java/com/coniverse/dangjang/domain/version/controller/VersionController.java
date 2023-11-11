package com.coniverse.dangjang.domain.version.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.version.dto.request.VersionRequest;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.service.VersionService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import given.apiversion.core.annotation.ApiVersion;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 앱 버전 관련 controller
 *
 * @author TEO
 * @since 1.3.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/version")
public class VersionController {
	private final VersionService versionService;

	/**
	 * 버전 정보를 GET 요청한다.
	 *
	 * @since 1.3.0
	 * @deprecated 1.4.0
	 */
	@Deprecated(since = "1.4.0")
	@GetMapping("/intro")
	public ResponseEntity<SuccessSingleResponse<VersionResponse<?>>> getIntro() {
		VersionResponse<?> versionResponse = versionService.getVersionResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), versionResponse));
	}

	/**
	 * 버전 정보를 조회한다.
	 *
	 * @return 버전 정보
	 * @since 1.4.0
	 */
	@ApiVersion("1")
	@GetMapping("/intro")
	public ResponseEntity<SuccessSingleResponse<VersionResponse<?>>> getIntroV1() {
		VersionResponse<?> versionResponse = versionService.getVersionResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), versionResponse));
	}

	/**
	 * 버전 정보를 POST 요청한다.
	 *
	 * @since 1.3.0
	 * @deprecated 1.4.0
	 */
	@Deprecated(since = "1.4.0")
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<VersionResponse<?>>> postVersion(@Valid @RequestBody VersionRequest request) {
		VersionResponse<?> versionResponse = versionService.saveVersion(request);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), versionResponse));
	}

	/**
	 * 버전 정보를 등록한다.
	 *
	 * @param request 버전 정보
	 * @return 등록된 버전 정보
	 * @since 1.4.0
	 */
	@ApiVersion("1")
	@PostMapping
	public ResponseEntity<SuccessSingleResponse<VersionResponse<?>>> postVersionV1(@Valid @RequestBody VersionRequest request) {
		VersionResponse<?> versionResponse = versionService.saveVersion(request);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), versionResponse));
	}
}
