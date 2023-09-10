package com.coniverse.dangjang.domain.guide.bloodsugar.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.coniverse.dangjang.domain.analysis.dto.AnalysisData;
import com.coniverse.dangjang.domain.analysis.dto.healthMetric.BloodSugarAnalysisData;
import com.coniverse.dangjang.domain.analysis.enums.Alert;
import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.code.enums.GroupCode;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.BloodSugarGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.document.SubGuide;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.CautionGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.GuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.HypoglycemiaGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.HypoglycemiaSuspectGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.NormalGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.guideformat.format.WarningGuideFormat;
import com.coniverse.dangjang.domain.guide.bloodsugar.mapper.BloodSugarGuideMapper;
import com.coniverse.dangjang.domain.guide.bloodsugar.repository.BloodSugarGuideRepository;
import com.coniverse.dangjang.domain.guide.common.dto.GuideResponse;
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
	public GuideResponse createGuide(AnalysisData analysisData) { // TODO summary 업데이트
		BloodSugarAnalysisData data = (BloodSugarAnalysisData)analysisData;
		BloodSugarGuide guide;
		try {
			guide = bloodSugarGuideSearchService.findByUserIdAndCreatedAt(data.getOauthId(), data.getCreatedAt());
		} catch (GuideNotFoundException e) {
			guide = mapper.toDocument(data);
		}
		GuideFormat guideFormat = getGuideFormat(data);
		SubGuide subGuide = mapper.toSubGuide(data, guideFormat);
		guide.add(subGuide);
		bloodSugarGuideRepository.save(guide);
		return mapper.toSubGuideResponseNoUnit(subGuide);
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

		GuideFormat guideFormat = getGuideFormat(data);
		SubGuide subGuide = mapper.toSubGuide(data, guideFormat);
		guide.update(subGuide);
		bloodSugarGuideRepository.save(guide);
		return mapper.toSubGuideResponseNoUnit(subGuide);

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

		GuideFormat guideFormat = getGuideFormat(data);
		SubGuide subGuide = mapper.toSubGuide(data, guideFormat);
		guide.update(subGuide, prevType);
		bloodSugarGuideRepository.save(guide);
		return mapper.toSubGuideResponseNoUnit(subGuide);
	}

	@Override
	public GroupCode getGroupCode() {
		return GroupCode.BLOOD_SUGAR;
	}

	private GuideFormat getGuideFormat(BloodSugarAnalysisData data) {
		if (data.getAlert().equals(Alert.HYPOGLYCEMIA)) {
			return Arrays.stream(HypoglycemiaGuideFormat.values())
				.filter(format -> format.verifyMedicine(data.isMedicine()))
				.filter(format -> format.verifyInjection(data.isInjection()))
				.findAny()
				.orElseThrow();
		} else if (data.getAlert().equals(Alert.HYPOGLYCEMIA_SUSPECT)) {
			return new HypoglycemiaSuspectGuideFormat(data.getDeviation());
		} else if (data.getAlert().equals(Alert.NORMAL)) {
			return NormalGuideFormat.getRandomOne();
		} else if (data.getAlert().equals(Alert.CAUTION)) {
			CautionGuideFormat format = new CautionGuideFormat(data.getDeviation(), data.isLackOfExercise(), data.isOverweight());
			format.setContent(data.isDiabetic(), data.getType());
			return format;
		}
		WarningGuideFormat format = new WarningGuideFormat(data.getDeviation(), data.isLackOfExercise(), data.isOverweight());
		format.setContent(data.isDiabetic());
		return format;
	}
}
