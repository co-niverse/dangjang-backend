package com.coniverse.dangjang.domain.version.service;

import static com.coniverse.dangjang.fixture.VersionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.coniverse.dangjang.domain.version.dto.request.VersionRequest;
import com.coniverse.dangjang.domain.version.dto.response.VersionResponse;
import com.coniverse.dangjang.domain.version.entity.Version;
import com.coniverse.dangjang.domain.version.repository.VersionRepository;

/**
 * @author TEO
 * @since 1.3.0
 */
class VersionServiceTest {
	private VersionService versionService;
	private VersionRepository versionRepository;
	private static final String KEY = "1234";
	private static final String MIN_VERSION = "1.3.1";
	private static final String LATEST_VERSION = "1.3.1";

	@BeforeEach
	void setUp() {
		versionRepository = Mockito.mock(VersionRepository.class);
		versionService = new VersionService(versionRepository, KEY);
	}

	@Test
	void 버전_응답을_반환한다() {
		// given
		Version version = 버전_엔티티(MIN_VERSION, LATEST_VERSION);
		doReturn(version).when(versionRepository).findFirstByOrderByCreatedAtDesc();

		// when
		VersionResponse<?> versionResponse = versionService.getVersionResponse();

		// then
		assertThat(versionResponse.minVersion()).isEqualTo(MIN_VERSION);
		assertThat(versionResponse.latestVersion()).isEqualTo(LATEST_VERSION);
	}

	@Test
	void 키가_같으면_버전을_성공적으로_저장한다() {
		// given
		doReturn(null)
			.when(versionRepository)
			.save(any());
		VersionRequest versionRequest = 버전_요청(MIN_VERSION, LATEST_VERSION, KEY);

		// when
		VersionResponse<?> versionResponse = versionService.saveVersion(versionRequest);

		// then
		assertThat(versionResponse.minVersion()).isEqualTo(MIN_VERSION);
		assertThat(versionResponse.latestVersion()).isEqualTo(LATEST_VERSION);
	}

	@Test
	void 키가_다르면_버전을_저장할_때_예외가_발생한다() {
		// given
		VersionRequest versionRequest = 버전_요청(MIN_VERSION, LATEST_VERSION, "wrong key");

		// when
		assertThatThrownBy(() -> versionService.saveVersion(versionRequest))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
