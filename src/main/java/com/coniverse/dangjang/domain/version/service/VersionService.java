package com.coniverse.dangjang.domain.version.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.version.dto.IntroResponse;
import com.coniverse.dangjang.domain.version.dto.Version;
import com.coniverse.dangjang.global.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

/**
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class VersionService {
	private static final String MIN_VERSION = Version.MINIMUM.getVersion();
	private static final String LATEST_VERSION = Version.LATEST.getVersion();
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
	 * TODO load data
	 *
	 * @return IntroResponse
	 * @since 1.0.0
	 */
	public IntroResponse<?> getProdIntroResponse() {
		IntroResponse<?> introResponse = new IntroResponse<>(MIN_VERSION, LATEST_VERSION, null);

		return introResponse;
	}
}
