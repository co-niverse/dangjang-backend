package com.coniverse.dangjang.domain.intro.controller;

import org.springframework.http.ResponseEntity;

import com.coniverse.dangjang.domain.intro.dto.IntroResponse;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

/**
 * client에서 앱을 처음 실행할 때, 앱의 버전을 확인하고, 최신 버전이 아니라면 업데이트를 유도하는 역할을 한다.
 *
 * @author TEO
 * @since 1.0.0
 */
public interface IntroController {
	ResponseEntity<SuccessSingleResponse<IntroResponse<?>>> getIntro();
}
