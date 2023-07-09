package com.coniverse.dangjang.domain.intro.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.intro.dto.IntroInfo;
import com.coniverse.dangjang.global.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntroService {
	private int error = -1;

	public IntroInfo getIntroInfoV1() {

		IntroInfo introInfo = new IntroInfo("1.0.0", "1.0.0");
		error += 1;
		if (error % 2 == 0) {
			return introInfo;
		}
		throw new BadRequestException();
	}
}
