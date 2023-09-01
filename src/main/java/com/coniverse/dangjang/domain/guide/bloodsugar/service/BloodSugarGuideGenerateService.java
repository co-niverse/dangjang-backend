package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.mapper.BloodSugarGuideMapper;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;

import lombok.RequiredArgsConstructor;

/**
 * 혈당 가이드 service
 *
 * @author TEO
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BloodSugarGuideGenerateService implements GuideGenerateService {
	private final BloodSugarGuideSearchService bloodSugarGuideSearchService;
	private final BloodSugarGuideRepository bloodSugarGuideRepository;
	private final BloodSugarGuideMapper mapper;

	/**
	 * 가이드를 생성하거나 업데이트한다.
	 *
	 * @param analysisData 혈당 분석 데이터
	 * @return 혈당 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public <T extends AnalysisData> GuideResponse generateGuide(T analysisData) {
		BloodSugarAnalysisData data = (BloodSugarAnalysisData)analysisData;
		String content = "가이드 내용입니다.";
		String summary = "가이드 요약입니다.";
		if (data.getGuideId() == null) {
			BloodSugarGuide guide = mapper.toDocument(data, content, summary);
			return mapper.toResponse(bloodSugarGuideRepository.save(guide));
		}
		return this.updateGuide(data, content, summary);
	}

	/**
	 * 가이드를 업데이트한다.
	 *
	 * @param analysisData 혈당 분석 데이터
	 * @param content      가이드 내용
	 * @param summary      가이드 요약
	 * @return 혈당 가이드 응답
	 */
	private GuideResponse updateGuide(BloodSugarAnalysisData analysisData, String content, String summary) {
		BloodSugarGuide guide = bloodSugarGuideSearchService.findById(analysisData.getGuideId());
		guide.update(analysisData, content, summary);
		return mapper.toResponse(bloodSugarGuideRepository.save(guide));
	}
}
