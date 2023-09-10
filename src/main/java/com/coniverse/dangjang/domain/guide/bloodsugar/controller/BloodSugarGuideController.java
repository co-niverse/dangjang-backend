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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guide/blood-sugar")
@RequiredArgsConstructor
@Validated
public class BloodSugarGuideController {
	private final BloodSugarGuideSearchService bloodSugarGuideSearchService;

	@GetMapping
	public ResponseEntity<SuccessSingleResponse<BloodSugarGuideResponse>> get(@ValidLocalDate @RequestParam String date,
		@AuthenticationPrincipal User principal) {
		BloodSugarGuideResponse response = bloodSugarGuideSearchService.findGuide(principal.getUsername(), date);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), response));
	}
}
