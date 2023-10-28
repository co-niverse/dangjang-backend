package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.BloodSugarGuideFormatFactory;
import com.coniverse.dangjang.domain.guide.bloodsugar.factory.guideformat.GuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.mapper.BloodSugarGuideMapper;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.dto.response.GuideResponse;
import com.coniverse.dangjang.domain.guide.common.exception.GuideNotFoundException;
import com.coniverse.dangjang.domain.guide.common.service.GuideGenerateService;

import lombok.RequiredArgsConstructor;

/**
 * 혈당 가이드 생성 service
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
	private final BloodSugarGuideFormatFactory bloodSugarGuideFormatFactory;

	/**
	 * 서브 가이드를 생성해서 혈당 가이드에 저장한다.
	 * <p>
	 * 혈당 가이드가 존재하면 새로운 서브 가이드를 추가하고, 존재하지 않으면 혈당 가이드를 생성하여 서브 가이드를 추가한다.
	 *
	 * @param analysisData 혈당 분석 데이터
	 * @return 서브 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse createGuide(AnalysisData analysisData) {
		BloodSugarAnalysisData data = (BloodSugarAnalysisData)analysisData;
		BloodSugarGuide guide;
		try {
			guide = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(data.getOauthId(), data.getCreatedAt());
		} catch (GuideNotFoundException e) {
			guide = mapper.toDocument(data);
		}
		GuideFormat guideFormat = bloodSugarGuideFormatFactory.createGuideFormat(data);
		SubGuide subGuide = mapper.toSubGuide(data, guideFormat);
		guide.addSubGuide(subGuide);
		bloodSugarGuideRepository.save(guide);
		return mapper.toSubGuideResponse(subGuide, guide.getTodayGuides());
	}

	/**
	 * 단위가 변경된 서브 가이드를 업데이트해서 혈당 가이드에 저장한다.
	 *
	 * @param analysisData 혈당 분석 데이터
	 * @return 서브 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse updateGuide(AnalysisData analysisData) {
		BloodSugarAnalysisData data = (BloodSugarAnalysisData)analysisData;
		BloodSugarGuide guide = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(data.getOauthId(), data.getCreatedAt());

		GuideFormat guideFormat = bloodSugarGuideFormatFactory.createGuideFormat(data);
		SubGuide subGuide = mapper.toSubGuide(data, guideFormat);
		guide.updateSubGuide(subGuide);
		bloodSugarGuideRepository.save(guide);
		return mapper.toSubGuideResponse(subGuide, guide.getTodayGuides());

	}

	/**
	 * 타입이 변경된 서브 가이드를 업데이트해서 혈당 가이드에 저장한다.
	 *
	 * @param analysisData 혈당 분석 데이터
	 * @param prevType     이전 타입
	 * @return 서브 가이드 응답
	 * @since 1.0.0
	 */
	@Override
	public GuideResponse updateGuideWithType(AnalysisData analysisData, CommonCode prevType) {
		BloodSugarAnalysisData data = (BloodSugarAnalysisData)analysisData;
		BloodSugarGuide guide = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(data.getOauthId(), data.getCreatedAt());

		GuideFormat guideFormat = bloodSugarGuideFormatFactory.createGuideFormat(data);
		SubGuide subGuide = mapper.toSubGuide(data, guideFormat);
		guide.updateSubGuide(subGuide, prevType);
		bloodSugarGuideRepository.save(guide);
		return mapper.toSubGuideResponse(subGuide, guide.getTodayGuides());
	}

	/**
	 * 서브 가이드를 삭제한다. 서브 가이드가 존재하지 않으면 혈당 가이드를 삭제한다.
	 *
	 * @param oauthId   유저 PK
	 * @param createdAt 생성일자
	 * @param type      타입
	 * @since 1.3.0
	 */
	@Override
	public void removeGuide(String oauthId, LocalDate createdAt, CommonCode type) {
		BloodSugarGuide guide = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(oauthId, createdAt);
		guide.removeSubGuide(type);
		if (guide.existsSubGuide()) {
			bloodSugarGuideRepository.save(guide);
			return;
		}
		bloodSugarGuideRepository.delete(guide);
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.BLOOD_SUGAR;
	}
}
