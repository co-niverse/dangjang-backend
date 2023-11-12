package com.coniverse.dangjang.domain.guide.bloodsugar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.guide.bloodsugar.dto.BloodSugarGuideResponse;
import com.coniverse.dangjang.domain.guide.bloodsugar.service.BloodSugarGuideSearchService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import given.apiversion.core.annotation.ApiVersion;
import lombok.RequiredArgsConstructor;

/**
 * 혈당 가이드 조회 controller
 *
 * @author TEO
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/guide/blood-sugar")
public class BloodSugarGuideController {
	private final BloodSugarGuideSearchService bloodSugarGuideSearchService;

	/**
	 * 혈당 가이드를 조회한다.
	 *
	 * @param date      조회할 일자
	 * @param principal 사용자 정보
	 * @return 혈당 가이드 응답 dto
	 * @since 1.0.0
	 * @deprecated 1.6.0
	 */
	@Deprecated(since = "1.6.0")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<BloodSugarGuideResponse>> get(@ValidLocalDate @RequestParam String date,
		@AuthenticationPrincipal User principal) {
		BloodSugarGuideResponse response = bloodSugarGuideSearchService.findGuide(principal.getUsername(), date);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}

	/**
	 * 혈당 가이드를 조회한다.
	 *
	 * @param date      조회할 일자
	 * @param principal 사용자 정보
	 * @return 혈당 가이드 응답 dto
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<BloodSugarGuideResponse>> getBloodSugarGuideV1(@ValidLocalDate @RequestParam String date,
		@AuthenticationPrincipal User principal) {
		BloodSugarGuideResponse response = bloodSugarGuideSearchService.findGuide(principal.getUsername(), date);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
