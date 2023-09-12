package com.coniverse.dangjang.domain.healthmetric.dto.request;

import java.util.Set;

/**
 * 건강지표 request post dto n개 (for health connect)
 *
 * @author TEO
 * @since 1.0.0
 */
public record HealthConnectPostRequest(Set<HealthMetricPostRequest> data) {
}
