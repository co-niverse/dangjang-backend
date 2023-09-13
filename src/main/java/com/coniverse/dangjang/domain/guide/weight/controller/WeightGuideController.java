package com.coniverse.dangjang.domain.guide.weight.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.guide.weight.dto.WeightGuideResponse;
import com.coniverse.dangjang.domain.guide.weight.service.WeightGuideSearchService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;
import com.coniverse.dangjang.global.validator.ValidLocalDate;

import lombok.RequiredArgsConstructor;

/**
 * 체중 가이드 Controller
 *
 * @author EVE
 * @since 1.0.0
 */

@RequestMapping("/api/guide/weight")
@RequiredArgsConstructor
@RestController
@Validated
public class WeightGuideController {
	private final WeightGuideSearchService weightGuideSearchService;

	/**
	 * 날짜별 체중 가이드 조회
	 *
	 * @param date      조회하는 날짜
	 * @param principal 유저 정보
	 * @author EVE
	 * @since 1.0.0
	 */
	@GetMapping
	public ResponseEntity<SuccessSingleResponse<WeightGuideResponse>> get(@ValidLocalDate @RequestParam String date,
		@AuthenticationPrincipal User principal) {
		WeightGuideResponse response = weightGuideSearchService.findGuide(principal.getUsername(), LocalDate.parse(date));
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
