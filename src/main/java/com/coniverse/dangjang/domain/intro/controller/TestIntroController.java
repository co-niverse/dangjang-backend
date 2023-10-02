package com.coniverse.dangjang.domain.intro.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.intro.dto.IntroResponse;
import com.coniverse.dangjang.domain.intro.service.IntroService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
@Profile({"dev", "local", "test"})
public class TestIntroController implements IntroController {
	private final IntroService introService;

	@Override
	@GetMapping("/test")
	public ResponseEntity<SuccessSingleResponse<IntroResponse<?>>> getIntro() {
		IntroResponse<?> introResponse = introService.getTestIntroResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), introResponse));
	}
}
