package com.coniverse.dangjang.domain.intro.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.intro.dto.IntroResponse;
import com.coniverse.dangjang.global.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

/**
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class IntroService {
	private static final String MIN_VERSION = "1.0.0";
	private static final String LATEST_VERSION = "1.0.0";
	private int error = -1;

	/**
	 * response를 성공적으로 반환 또는 예외 발생을 번갈아가며 실행한다.
	 *
	 * @return IntroResponse
	 * @since 1.0.0
	 */
	public IntroResponse<?> getTestIntroResponse() {
		IntroResponse<?> introResponse = new IntroResponse<>(MIN_VERSION, LATEST_VERSION, null);
		error += 1;
		if (error % 2 == 0) {
			return introResponse;
		}
		throw new BadRequestException();
	}

	/**
	 * TODO
	 *
	 * @return IntroResponse
	 * @since 1.0.0
	 */
	public IntroResponse<?> getProdIntroResponse() {
		IntroResponse<?> introResponse = new IntroResponse<>(MIN_VERSION, LATEST_VERSION, null);

		return introResponse;
	}
}
