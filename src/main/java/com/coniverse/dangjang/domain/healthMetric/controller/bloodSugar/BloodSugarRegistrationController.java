package com.coniverse.dangjang.domain.healthMetric.controller.bloodSugar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coniverse.dangjang.domain.healthMetric.controller.HealthMetricRegistrationController;
import com.coniverse.dangjang.domain.healthMetric.dto.request.HealthMetricRequest;
import com.coniverse.dangjang.domain.healthMetric.dto.response.HealthMetricResponse;
import com.coniverse.dangjang.domain.healthMetric.service.bloodSugar.BloodSugarRegistrationService;
import com.coniverse.dangjang.global.dto.SuccessSingleResponse;

import lombok.RequiredArgsConstructor;

/**
 * 혈당정보 등록 controller이다.
 *
 * @author TEO
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/health-metric/blood-sugar")
@RequiredArgsConstructor
public class BloodSugarRegistrationController implements HealthMetricRegistrationController {
	private final BloodSugarRegistrationService bloodSugarRegistrationService;

	@Override
	@PostMapping("/{month}/{day}")
	public ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> post(int month, int day, HealthMetricRequest bloodSugarRequest) {
		HealthMetricResponse bloodSugarResponse = bloodSugarRegistrationService.save(bloodSugarRequest);
		return ResponseEntity.ok().body(new SuccessSingleResponse<>(HttpStatus.OK.getReasonPhrase(), bloodSugarResponse));
	}

	@Override
	@PatchMapping("/{month}/{day}")
	public ResponseEntity<SuccessSingleResponse<HealthMetricResponse>> patch(int month, int day,
		HealthMetricRequest bloodSugarRequest) {
		return null;
	}
}
