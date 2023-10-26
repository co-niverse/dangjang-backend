package com.coniverse.dangjang.domain.version.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.version.dto.IntroResponse;
import com.coniverse.dangjang.domain.version.service.VersionService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/version")
@RequiredArgsConstructor
public class VersionController {
	private final VersionService versionService;

	@GetMapping("/intro")
	public ResponseEntity<SuccessSingleResponse<IntroResponse<?>>> getIntro() {
		IntroResponse<?> introResponse = versionService.getProdIntroResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), introResponse));
	}
}
