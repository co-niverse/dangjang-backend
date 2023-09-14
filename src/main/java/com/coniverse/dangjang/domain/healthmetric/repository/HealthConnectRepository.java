package com.coniverse.dangjang.domain.healthmetric.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;

import lombok.RequiredArgsConstructor;

/**
 * health connect 데이터를 batch insert 하기 위한 repository
 *
 * @author TEO
 * @since 1.0.0
 */
@Repository
@RequiredArgsConstructor
public class HealthConnectRepository {
	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public void insertBatch(List<HealthMetric> healthMetrics) {
		String query = "INSERT IGNORE INTO health_metric (oauth_id, created_at, type, group_code, unit) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(
			query,
			healthMetrics,
			healthMetrics.size(),
			(ps, healthMetric) -> {
				ps.setString(1, healthMetric.getUser().getOauthId());
				ps.setString(2, healthMetric.getCreatedAt().toString());
				ps.setString(3, healthMetric.getType().getCode());
				ps.setString(4, healthMetric.getGroupCode().getCode());
				ps.setString(5, healthMetric.getUnit());
			}
		);
	}
}
