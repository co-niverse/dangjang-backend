package com.coniverse.dangjang.domain.version.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.version.dto.IntroResponse;
import com.coniverse.dangjang.domain.version.service.IntroService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
public class ProdIntroController {
	private final IntroService introService;

	@GetMapping("/prod")
	public ResponseEntity<SuccessSingleResponse<IntroResponse<?>>> getIntro() {
		IntroResponse<?> introResponse = introService.getProdIntroResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), introResponse));
	}
}
