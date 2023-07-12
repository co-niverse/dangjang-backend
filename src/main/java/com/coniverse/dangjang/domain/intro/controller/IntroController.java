package com.coniverse.dangjang.domain.intro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.intro.dto.IntroResponse;
import com.coniverse.dangjang.domain.intro.service.IntroService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

/**
 * client에서 앱을 처음 실행할 때, 앱의 버전을 확인하고, 최신 버전이 아니라면 업데이트를 유도하는 역할을 한다.
 *
 * @author TEO
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/intro")
@RequiredArgsConstructor
public class IntroController {
	private final IntroService introService;

	@GetMapping("/test")
	public ResponseEntity<SuccessSingleResponse<IntroResponse<?>>> getTestIntro() {
		IntroResponse<?> introResponse = introService.getTestIntroResponse();
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), introResponse));
	}
}
