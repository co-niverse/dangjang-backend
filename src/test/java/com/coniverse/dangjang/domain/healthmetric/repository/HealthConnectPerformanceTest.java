package com.coniverse.dangjang.domain.healthmetric.repository;

import static com.coniverse.dangjang.fixture.HealthMetricFixture.*;
import static com.coniverse.dangjang.fixture.UserFixture.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.coniverse.dangjang.domain.code.enums.CommonCode;
import com.coniverse.dangjang.domain.healthmetric.dto.request.HealthConnectPostRequest;
import com.coniverse.dangjang.domain.healthmetric.entity.HealthMetric;
import com.coniverse.dangjang.domain.healthmetric.mapper.HealthMetricMapper;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.domain.user.repository.UserRepository;
import com.coniverse.dangjang.domain.user.service.UserSearchService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Disabled
@ActiveProfiles("performance")
public class HealthConnectPerformanceTest {
	@Autowired
	private HealthMetricRepository healthMetricRepository;
	@Autowired
	private HealthConnectRepository healthConnectRepository;
	@Autowired
	private HealthMetricMapper healthMetricMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserSearchService userSearchService;
	private static final String type = CommonCode.BEFORE_BREAKFAST.getTitle();
	private static final int count = 10000;
	private static final int duplicatedCount = 3000;
	private String oauthId;
	@PersistenceContext
	private EntityManager em;

	@BeforeAll
	void setUp() {
		oauthId = userRepository.save(유저_테오()).getOauthId();
	}

	@Test
	void n번_조회해서_중복을_제거한_후_saveAll_bulk_insert() {
		// given
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, count);
		final User user = userSearchService.findUserByOauthId(oauthId);

		// when
		long startTime = System.currentTimeMillis();
		List<HealthMetric> healthMetrics = request.data()
			.stream()
			.map(postRequest -> healthMetricMapper.toEntity(postRequest, user))
			.filter(h -> healthMetricRepository
				.findByHealthMetricId(oauthId, h.getCreatedAt(), h.getType())
				.isEmpty()
			)
			.toList();
		healthMetricRepository.saveAll(healthMetrics);
		em.flush();
		long endTime = System.currentTimeMillis();

		// then
		System.out.println("--------------------------");
		System.out.println("건강지표 추가된 개수: " + healthMetrics.size());
		System.out.println("걸린 시간: " + (endTime - startTime) + "ms");
		System.out.println("저장된 건강지표 개수:" + healthMetricRepository.findAll().size());
		System.out.println("--------------------------");
	}

	@Test
	void jdbc_template를_사용해서_insert_ignore_query와_batchUpdate() {
		// given
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, count);
		final User user = userSearchService.findUserByOauthId(oauthId);

		// when
		long startTime = System.currentTimeMillis();
		List<HealthMetric> healthMetrics = request.data()
			.stream()
			.map(data -> healthMetricMapper.toEntity(data, user))
			.toList();
		healthConnectRepository.insertBatch(healthMetrics);
		long endTime = System.currentTimeMillis();

		// then
		System.out.println("--------------------------");
		System.out.println("걸린 시간: " + (endTime - startTime) + "ms");
		System.out.println("저장된 건강지표 개수:" + healthMetricRepository.findAll().size());
		System.out.println("--------------------------");
	}

	@Test
	void db에_중복_데이터를_추가하고_n번_조회해서_중복을_제거한_후_saveAll_bulk_insert() {
		// 중복 데이터 저장
		HealthConnectPostRequest prevRequest = 헬스_커넥트_등록_요청(type, duplicatedCount);
		final User prevUser = userSearchService.findUserByOauthId(oauthId);

		List<HealthMetric> prevHealthMetrics = prevRequest.data()
			.stream()
			.map(postRequest -> healthMetricMapper.toEntity(postRequest, prevUser))
			.toList();
		healthMetricRepository.saveAll(prevHealthMetrics);
		em.flush();
		em.clear();

		// given
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, count);
		final User user = userSearchService.findUserByOauthId(oauthId);

		// when
		long startTime = System.currentTimeMillis();
		List<HealthMetric> healthMetrics = request.data()
			.stream()
			.map(postRequest -> healthMetricMapper.toEntity(postRequest, user))
			.filter(h -> healthMetricRepository
				.findByHealthMetricId(oauthId, h.getCreatedAt(), h.getType())
				.isEmpty()
			)
			.toList();
		healthMetricRepository.saveAll(healthMetrics);
		em.flush();
		long endTime = System.currentTimeMillis();

		// then
		System.out.println("--------------------------");
		System.out.println("건강지표 추가된 개수: " + healthMetrics.size());
		System.out.println("걸린 시간: " + (endTime - startTime) + "ms");
		System.out.println("저장된 건강지표 개수:" + healthMetricRepository.findAll().size());
		System.out.println("--------------------------");
	}

	@Test
	void db에_중복_데이터를_추가하고_jdbc_template를_사용해서_insert_ignore_query와_batchUpdate() {
		// 중복 데이터 저장
		HealthConnectPostRequest prevRequest = 헬스_커넥트_등록_요청(type, duplicatedCount);
		final User prevUser = userSearchService.findUserByOauthId(oauthId);

		List<HealthMetric> prevHealthMetrics = prevRequest.data()
			.stream()
			.map(data -> healthMetricMapper.toEntity(data, prevUser))
			.toList();
		healthConnectRepository.insertBatch(prevHealthMetrics);
		em.clear();

		// given
		HealthConnectPostRequest request = 헬스_커넥트_등록_요청(type, count);
		final User user = userSearchService.findUserByOauthId(oauthId);

		// when
		long startTime = System.currentTimeMillis();
		List<HealthMetric> healthMetrics = request.data()
			.stream()
			.map(data -> healthMetricMapper.toEntity(data, user))
			.toList();
		healthConnectRepository.insertBatch(healthMetrics);
		long endTime = System.currentTimeMillis();

		// then
		System.out.println("--------------------------");
		System.out.println("걸린 시간: " + (endTime - startTime) + "ms");
		System.out.println("저장된 건강지표 개수:" + healthMetricRepository.findAll().size());
		System.out.println("--------------------------");
	}

}
