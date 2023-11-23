package com.coniverse.dangjang.domain.guide.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.guide.common.dto.response.DayGuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.DayGuideService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import given.apiversion.core.annotation.ApiVersion;
import lombok.RequiredArgsConstructor;

/**
 * 하루 가이드 조회 Controller
 *
 * @author EVE
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/guide")
public class GuideController {
	private final DayGuideService dayGuideService;

	/**
	 * 하루 요약 가이드를 조회하여 전달한다
	 *
	 * @param date 조회날짜
	 * @author EVE
	 * @since 1.6.0
	 */
	@ApiVersion("1")
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<DayGuideResponse>> getDayGuideV1(@ValidLocalDate @RequestParam String date,
		@AuthenticationPrincipal User principal) {
		DayGuideResponse dayGuideResponse = dayGuideService.getDayGuide(principal.getUsername(), date);
		return ResponseEntity.ok()
			.body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), dayGuideResponse));
	}
}
