package com.coniverse.dangjang.domain.guide.weight.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.guide.weight.document.WeightGuide;
import com.coniverse.dangjang.domain.guide.weight.repository.WeightGuideRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeightGuideSearchService {
	private final WeightGuideRepository weightGuideRepository;

	public WeightGuide findByOauthIdAndCreatedAt(String oauthId, LocalDate createdAt) {
		return weightGuideRepository.findByOauthIdAndCreatedAt(oauthId, createdAt);
	}
}
